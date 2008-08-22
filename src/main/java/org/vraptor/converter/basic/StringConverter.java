package org.vraptor.converter.basic;

import org.vraptor.LogicRequest;
import org.vraptor.converter.Converter;

/**
 * String converter
 * 
 * @author Guilherme Silveira
 */
public class StringConverter implements Converter {

	public Object convert(String value, Class<?> type, LogicRequest context) {
		return value;
	}

	public boolean canConvert(Class<?> type) {
		return type.isAssignableFrom(String.class);
	}

	/**
	 * Returns the list of supported types
	 * 
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { String.class };
	}

}
