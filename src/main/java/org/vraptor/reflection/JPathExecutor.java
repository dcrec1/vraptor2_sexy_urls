package org.vraptor.reflection;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.Conversion;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;
import org.vraptor.converter.ConverterManager;
import org.vraptor.introspector.ReadParameter;

/**
 * JPath setter execution for a specific logiccontext.
 * 
 * @author Guilherme Silveira
 */
public class JPathExecutor {

	private static final Logger LOG = Logger.getLogger(JPathExecutor.class);

	private final ConverterManager manager;

	private final LogicRequest context;

	private final Object[] methodParams;

	private final Object component;

	public JPathExecutor(ConverterManager converters, LogicRequest logicRequest, Object methodParams[], Object component) {
		this.manager = converters;
		this.context = logicRequest;
		this.methodParams = methodParams;
		this.component = component;
	}

	/**
	 * Tries to set some property in the current object. It uses the path array
	 * to walk in the object graph, the matching field is called field and the
	 * value is either completeValue or arrayValue
	 * 
	 * @param object
	 *            the object to set the property
	 * @param path
	 *            the path to walk
	 * @param completeValue
	 *            the completeValue
	 * @param arrayValue
	 *            the array value
	 * @param field
	 *            the field to use
	 * @throws SettingException
	 *             some exception ocurred while trying to set a value
	 * @throws ConversionException
	 *             some convesion exception occurred
	 */
	public void set(String[] path, String completeValue, String[] arrayValue, ReadParameter read)
			throws SettingException, ConversionException {

		if (path.length == 1) {
			try {
				Object value = convert(completeValue, arrayValue, read.getType(), read.getOverridenConverter());
				read.set(component, methodParams, value);
			} catch (ConversionException e) {
				e.setCategory(path[0]);
				throw e;
			}
			return;
		}

		// moves to the first field
		Object object = read.guaranteeExistence(component, methodParams);
		if (object == null) {
			return;
		}

		int start = 1, len = path.length;

		// navigates on the first array, one depth only
		if (len != 2 && Character.isDigit(path[start].charAt(0))) {
			SetDealer dealer = SetDealerFactory.getDealer(object);
			try {
				int arrayPosition = Integer.parseInt(path[start]);
				object = dealer.resizeAndSet(object, arrayPosition, read.mightCreate(), read.getGenericType());
				// calls the setter
				read.set(component, methodParams, object);
				// retrieves the specified position
				object = dealer.getPosition(object, arrayPosition, read.mightCreate());
				start++;
			} catch (NumberFormatException e) {
				throw new SettingException("Invalid array index: " + path[1]);
			}
		}

		if (object == null) {
			return;
		}

		internalSet(path, completeValue, arrayValue, read, object, start);
	}

	private void internalSet(String[] path, String completeValue, String[] arrayValue,
			ReadParameter read, Object object, int start) throws SettingException, ConversionException {
		try {

			for (int i = start; i < path.length - 1; i++) {

				Object currentObject = object;

				// for each parameter, calls the getter method
				Method method = ReflectionUtil.findGetter(object.getClass(), path[i]);
				// if no getter found, forget it!
				if (method == null) {
					return;
				}
				object = ReflectionUtil.invoke(object, method);
				Class returnType = method.getReturnType();
				if (object == null) {
					if (read.mightCreate()) {
						try {
							// my getter returned null... i should instantiate
							// it
							if (isCollection(returnType)) {
								object = ReflectionUtil.instantiateCollection(returnType);
							} else {
								object = ReflectionUtil.genericInstantiate(returnType);
							}
							// calls the setter
							ReflectionUtil.invoke(currentObject, ReflectionUtil.findSetter(currentObject, path[i]),
									object);
						} catch (ComponentInstantiationException e) {
							throw new SettingException(e.getMessage(), e);
						}
					} else {
						return;
					}
				}

				// if the next is an array index, use it
				while (i < path.length && Character.isDigit(path[i + 1].charAt(0))) {
					try {
						int arrayPosition = Integer.parseInt(path[i + 1]);
						SetDealer dealer = SetDealerFactory.getDealer(object);
						object = dealer.resizeAndSet(object, arrayPosition, read.mightCreate(), method
								.getGenericReturnType());
						// calls the setter
						ReflectionUtil.invoke(currentObject, ReflectionUtil.findSetter(currentObject, path[i]), object);
						// retrieves the specified position
						object = dealer.getPosition(object, arrayPosition, read.mightCreate());
						if (object == null) {
							return;
						}
					} catch (NumberFormatException e) {
						throw new SettingException("Invalid array index: " + path[1]);
					}
					i++;
				}

			}

			String lastPath = path[path.length - 1];

			if (Character.isDigit(lastPath.charAt(0))) {
				// if the last path is an array...
				SetDealer dealer = SetDealerFactory.getDealer(object);
				try {
					int arrayPosition = Integer.parseInt(lastPath);
					// last level... convert and set array position
					Object value = convert(completeValue, arrayValue, dealer.getType(read.getGenericType()), read
							.getOverridenConverter());
					object = dealer.resizeAndSet(object, arrayPosition, value);
					// calls the setter
					read.set(component, methodParams, object);
					return;
				} catch (NumberFormatException e) {
					throw new SettingException("Invalid array index " + path[1]);
				}
			}

			// calls the setter for the last one
			Method setter = ReflectionUtil.findSetter(object, lastPath);
			if (setter == null) {
				return;
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("ready to use parameter " + Arrays.toString(path));
			}
			
			Conversion annotation = setter.getAnnotation(Conversion.class);
			Class<? extends Converter> overriden = annotation == null ? null : annotation.value();
			Object result = convert(completeValue, arrayValue, setter.getParameterTypes()[0], overriden);
			ReflectionUtil.invoke(object, setter, result);

		} catch (MethodInvocationException e) {
			throw new SettingException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new SettingException(e.getMessage(), e);
		} catch (SecurityException e) {
			throw new SettingException(e.getMessage(), e);
		} catch (ConversionException e) {
			e.setCategory(path[path.length - 1]);
			throw e;
		}
	}

	/**
	 * Converts a value either by its type or overriden converter.
	 */
	private Object convert(String completeValue, String[] arrayValue, Class type, Class<? extends Converter> converter)
			throws ConversionException {
		return this.manager.convert(arrayValue, completeValue, type, context, converter);
	}

	private boolean isCollection(Class<?> type) {
		return Collection.class.isAssignableFrom(type);
	}

}
