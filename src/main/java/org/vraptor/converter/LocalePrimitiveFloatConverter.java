package org.vraptor.converter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.i18n.JstlWrapper;

/**
 * Locale based float converter. Uses the error key invalid_value if unable to
 * parse its information.
 * 
 * @author Nico Steppat
 */
public class LocalePrimitiveFloatConverter implements Converter {

	private final JstlWrapper jstlWrapper = new JstlWrapper();

	private static final Logger LOG = Logger.getLogger(LocalePrimitiveFloatConverter.class);

	public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {

		if(value == null || value.equals("")) {
			throw new ConversionException("invalid_value", "Unable to parse string " + value);
		}
		
		Locale locale = jstlWrapper.findLocale((LogicRequest) context);
		if (locale == null) {
			locale = Locale.getDefault();
		}

		try {
			Number number = NumberFormat.getInstance(locale).parse(value);
			return number.floatValue();
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
		return new Class[] { float.class };
	}
}
