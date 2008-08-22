package org.vraptor.converter;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.converter.basic.BasicConverters;
import org.vraptor.reflection.ReflectionUtil;

/**
 * Converter repository.
 * 
 * @author Guilherme Silveira
 */
public class SimpleConverterManager implements ConverterManager {

	private static final Logger LOG = Logger.getLogger(SimpleConverterManager.class);

	private final LinkedList<Converter> converters;

	private final Map<Class<?>, Converter> cachedConverters;

	public SimpleConverterManager() {
		cachedConverters = new HashMap<Class<?>, Converter>();
		converters = new LinkedList<Converter>();
		BasicConverters.register(this);
	}

	/**
	 * Registers a new converter
	 * 
	 * @param converter
	 *            the converter itself
	 */
	public void register(Converter converter) {
		this.converters.addFirst(converter);
		cachedConverters.clear();
	}

	public Object convert(String[] arrayValue, String completeValue, Class<?> originalType, LogicRequest context,
			Class<? extends Converter> overridenConverter) throws ConversionException {

		Class<?> type = originalType.isArray() ? originalType.getComponentType() : originalType;

		Converter converter = overridenConverter == null ? findConverter(type) : getConverter(overridenConverter);

		boolean isArray = originalType.isArray();

		if (isArray) {

			Object converted = Array.newInstance(type, arrayValue.length);
			for (int i = 0; i < arrayValue.length; i++) {
				Array.set(converted, i, converter.convert(arrayValue[i], type, context));
			}
			return converted;

		} else {
			return converter.convert(completeValue, type, context);
		}

	}

	private synchronized Converter getConverter(Class<? extends Converter> converter) throws ConversionException {
		try {
			return (Converter) ReflectionUtil.instantiate(converter);
		} catch (ComponentInstantiationException e) {
			throw new ConversionException("invalid_converter", e.getMessage(), e);
		}
	}

	/**
	 * Finds a specific converter for a type
	 * 
	 * @param type
	 *            type
	 * @return conveter
	 * @throws ConversionException
	 *             no converter was found
	 */
	private Converter findConverter(Class<?> type) throws ConversionException {
		Converter converter = this.cachedConverters.get(type);
		if (converter == null) {
			for (Converter tempConverter : converters) {
				if (correctConverter(tempConverter, type)) {
					converter = tempConverter;
					break;
				}
			}
			if (converter == null) {
				throw new ConversionException("no_converter_found", "Unable to find converter for " + type.getName());
			}
		}
		LOG.debug("Found converter " + converter.getClass().getName());
		return converter;
	}

	/**
	 * Is it the correct converter for this type?
	 * 
	 * @param converter
	 *            the converter
	 * @param type
	 *            the type
	 * @return true if responsible for this type
	 */
	@SuppressWarnings("unchecked")
	private boolean correctConverter(Converter converter, Class<?> type) {
		for (Class supportedType : converter.getSupportedTypes()) {
			if (supportedType.isAssignableFrom(type)) {
				cachedConverters.put(type, converter);
				return true;
			}
		}
		return false;
	}

}
