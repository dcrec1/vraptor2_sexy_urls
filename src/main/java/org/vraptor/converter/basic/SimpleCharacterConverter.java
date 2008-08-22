package org.vraptor.converter.basic;

import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Converts to Character. Uses the error key invalid_character if unable to
 * parse its information.
 * 
 * @author Guilherme Silveira
 */
public class SimpleCharacterConverter implements Converter {

	/**
	 * Converts
	 * 
	 * @see org.vraptor.converter.Converter#convert(java.lang.String,
	 *      java.lang.Class, org.vraptor.scope.LogicRequest)
	 */
	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		if (value == null || value.equals("")) {
			return null;
		}
		if (value.length() != 1) {
			throw new ConversionException("invalid_character",
					"Content is more than a single character");
		}
		return Character.valueOf(value.charAt(0));
	}

	/**
	 * Returns the list of supported types
	 * 
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { Character.class };
	}

}
