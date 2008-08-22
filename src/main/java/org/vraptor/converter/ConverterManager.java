package org.vraptor.converter;

import org.vraptor.LogicRequest;

/**
 * Manages conversion
 * 
 * @author Guilherme Silveira
 */
public interface ConverterManager {

	/**
	 * Registers a new converter
	 * 
	 * @param converter
	 *            the converter itself
	 */
	void register(Converter converter);

	/**
	 * Performs convertion
	 * 
	 * @param arrayValue
	 *            array value
	 * @param completeValue
	 *            complete value in a string
	 * @param originalType
	 *            original type
	 * @param context
	 *            context
	 * @return the converted value
	 * @throws ConversionException
	 *             some problem has occured
	 */
	Object convert(String[] arrayValue, String completeValue,
			Class<?> originalType, LogicRequest context,
			Class<? extends Converter> overridenConverter)
			throws ConversionException;

}
