package org.vraptor.converter.basic;

import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Simple short converter.
 * Uses the error key invalid_number if unable to parse its information. 
 * @author Guilherme Silveira
 */
public class SimpleShortConverter implements Converter {

	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		if (value == null || value.equals("")) {
			return null;
		}
		try {
			return Short.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ConversionException("invalid_number", e.getMessage(), e);
		}
	}

	/**
	 * Returns the list of supported types
	 * 
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { Short.class };
	}

}
