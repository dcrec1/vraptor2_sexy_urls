package org.vraptor.converter.basic;

import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Simple char converter.
 * Uses the error key invalid_character if unable to parse its information.
 * 
 * @author Guilherme Silveira
 */
public class PrimitiveCharConverter implements Converter {

	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		if (value.length() != 1) {
			throw new ConversionException("invalid_character",
					"Content is more than a single character");
		}
		return value.charAt(0);
	}

	/**
	 * Returns the list of supported types
	 * 
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { char.class };
	}

}
