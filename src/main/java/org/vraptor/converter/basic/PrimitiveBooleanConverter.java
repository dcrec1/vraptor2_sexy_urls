package org.vraptor.converter.basic;

import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * <p>
 * Primitive boolean converter. For the wrapper class {@link Boolean} see
 * {@link SimpleBooleanConverter}.
 * </p>
 * <p>
 * Uses the error key invalid_boolean if unable to parse its information.
 * </p>
 *
 * @author Guilherme Silveira
 * @author Fabio Kung
 *
 * @see SimpleBooleanConverter
 */
public class PrimitiveBooleanConverter implements Converter {

	public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
		return Boolean.parseBoolean(value);
	}

	/**
	 * Returns the list of supported types
	 *
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { boolean.class };
	}

}
