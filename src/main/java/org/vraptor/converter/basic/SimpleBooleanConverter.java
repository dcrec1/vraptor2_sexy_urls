package org.vraptor.converter.basic;

import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * <p>
 * Simple Boolean (wrapper class) converter. For primitive boolean see
 * {@link PrimitiveBooleanConverter}.
 * </p>
 * <p>
 * Uses the error key invalid_boolean if unable to parse its information.
 * </p>
 *
 * @author Guilherme Silveira
 * @author Fabio Kung
 *
 * @see PrimitiveBooleanConverter
 */
public class SimpleBooleanConverter implements Converter {

	/**
	 *
	 * @see org.vraptor.converter.Converter#convert(java.lang.String,
	 *      java.lang.Class, org.vraptor.scope.LogicRequest)
	 */
	public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
		if (value == null || value.equals("")) {
			return null;
		}
		return Boolean.valueOf(value);
	}

	/**
	 * Returns the list of supported types
	 *
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { Boolean.class };
	}

}
