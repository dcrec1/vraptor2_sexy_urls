package org.vraptor.converter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.i18n.JstlWrapper;

/**
 * Locale based Double converter. Uses the error key invalid_value if unable to
 * parse its information.
 * 
 * @author Nico Steppat
 */
public class LocaleDoubleConverter implements Converter {

	private final JstlWrapper jstlWrapper = new JstlWrapper();

	private static final Logger LOG = Logger.getLogger(LocaleDoubleConverter.class);

	public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {

		if (value == null || value.equals("")) {
			return null;
		}
		Locale locale = jstlWrapper.findLocale((LogicRequest) context);
		if (locale == null) {
			locale = Locale.getDefault();
		}

		try {
			Number number = NumberFormat.getInstance(locale).parse(value);
			return new Double(number.doubleValue());
		} catch (ParseException e) {
			LOG.error("Unable to parse string " + value, e);
			throw new ConversionException("invalid_value", "Unable to parse string " + value, e);
		}
	}

	/**
	 * Returns the list of supported types
	 * 
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { Double.class };
	}

}
