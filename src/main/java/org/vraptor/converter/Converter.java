package org.vraptor.converter;

import org.vraptor.LogicRequest;

/**
 * Basic converter.
 * 
 * @author Guilherme Silveira
 */
public interface Converter {

	/**
	 * Converts a value to an specific type
	 * 
	 * @param value
	 *            current value
	 * @param type
	 *            desired type
	 * @return the converted value
	 * @throws ConversionException
	 *             some convertion problem hapenned
	 */
	Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException;

	/**
	 * Returns the list of supported types
	 * 
	 * @return array of supported types
	 */
	Class<?>[] getSupportedTypes();

}
