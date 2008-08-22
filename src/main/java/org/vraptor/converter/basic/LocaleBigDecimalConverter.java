package org.vraptor.converter.basic;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;
import org.vraptor.i18n.JstlWrapper;

/**
 * Locale based BigDecimal converter. Uses the error key invalid_value if unable
 * to parse its information.
 * 
 * @author Ricardo A. F. da Cruz
 */
public class LocaleBigDecimalConverter implements Converter {

	private final JstlWrapper jstlWrapper = new JstlWrapper();

	private static final Logger LOG = Logger
			.getLogger(LocaleBigDecimalConverter.class);

	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {

		if (value == null || value.equals("")) {
			return null;
		}
		Locale locale = jstlWrapper.findLocale((LogicRequest) context);
		if (locale == null) {
			locale = Locale.getDefault();
		}

		try {
			Number number = NumberFormat.getInstance(locale).parse(value);
			return new BigDecimal(number.toString());
		} catch (ParseException e) {
			LOG.error("Unable to parse string " + value, e);
			throw new ConversionException("invalid_value",
					"Unable to parse string " + value, e);
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
