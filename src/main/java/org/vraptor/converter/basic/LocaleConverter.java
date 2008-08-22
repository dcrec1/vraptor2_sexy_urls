package org.vraptor.converter.basic;

import java.util.Locale;

import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;
import org.vraptor.i18n.JstlWrapper;

/**
 * Converter capable of dealing with instances of java.util.Locale.
 * 
 * @author Guilherme Silveira
 */
public class LocaleConverter implements Converter {

	private final JstlWrapper jstlWrapper = new JstlWrapper();

	public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
		if (value == null || value.equals("")) {
			return null;
		}
		return jstlWrapper.stringToLocale(value);
	}

	public Class<?>[] getSupportedTypes() {
		return new Class[] { Locale.class };
	}

}
