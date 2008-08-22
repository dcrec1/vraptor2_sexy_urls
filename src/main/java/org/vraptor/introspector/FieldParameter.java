package org.vraptor.introspector;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.vraptor.annotations.Conversion;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.converter.Converter;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.reflection.SettingException;

/**
 * A field based in parameter.
 * 
 * @author Guilherme Silveira
 * @since 2.2
 */
public class FieldParameter implements ReadParameter {

	private static final Logger LOG = Logger.getLogger(FieldParameter.class);

	private final Field field;

	private final Class<? extends Converter> converter;

	private final String key;

	private final boolean mightCreate;

	public FieldParameter(FieldAnnotation<org.vraptor.annotations.Parameter> info) {
		org.vraptor.annotations.Parameter param = info.getAnnotation();
		this.field = info.getField();
		this.key = new KeyExtractor().extractParamKey(info);
		this.mightCreate = param.create();
		if (field.isAnnotationPresent(Conversion.class)) {
			this.converter = field.getAnnotation(Conversion.class).value();
		} else {
			this.converter = null;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Created new FieldReadParameter " + this.key);
		}
	}

	/**
	 * The parameter key
	 * 
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Should it instantiate required fields on the fly?
	 * 
	 * @return true if it should instantiate them
	 */
	public boolean mightCreate() {
		return this.mightCreate;
	}

	/**
	 * Returns the overriden converter class attached to this read parameter
	 * 
	 * @return the converter class
	 */
	public Class<? extends Converter> getOverridenConverter() {
		return this.converter;
	}

	/**
	 * 
	 * @throws SettingException
	 *             setting exception
	 * @see org.vraptor.introspector.ReadParameter#guaranteeExistence(java.lang.Object)
	 */
	public Object guaranteeExistence(Object component, Object[] methodParams) throws SettingException {
		try {
			Object value = ReflectionUtil.get(component, field);
			if (value == null && mightCreate()) {
				if (Collection.class.isAssignableFrom(field.getType())) {
					value = ReflectionUtil.instantiateCollection(field.getType());
				} else {
					value = ReflectionUtil.genericInstantiate(field.getType());
				}
				ReflectionUtil.setField(component, field, value);
			}
			return value;
		} catch (GettingException e) {
			throw new SettingException(e.getMessage(), e);
		} catch (ComponentInstantiationException e) {
			throw new SettingException(e.getMessage(), e.getCause());
		}

	}

	public Class<?> getType() {
		return field.getType();
	}

	public void set(Object component, Object[] methodParams, Object value) throws SettingException {
		ReflectionUtil.setField(component, field, value);
	}

	public Type getGenericType() {
		return field.getGenericType();
	}

}
