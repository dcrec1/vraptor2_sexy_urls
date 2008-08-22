package org.vraptor.converter.basic;

import java.math.BigDecimal;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Simple BigDecimal converter.
 * Uses the error key invalid_time if unable to parse its information.
 * 
 * @author Ricardo A. F. da Cruz
 */
public class SimpleBigDecimalConverter implements Converter {
	
	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		if (value == null || value.equals("")) {
			return null;
		}
		try {
			return new BigDecimal(value);
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
		return new Class[] { BigDecimal.class };
	}

}
