package org.vraptor.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.vraptor.LogicRequest;
import org.vraptor.i18n.JstlWrapper;

/**
 * Locale based calendar converter. Uses the error key invalid_time if unable to
 * parse its information.
 * 
 * @author Guilherme Silveira
 */
public class LocaleCalendarTimeConverter implements Converter {

	private final JstlWrapper jstlWrapper = new JstlWrapper();

	public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
		if (value == null || value.equals("")) {
			return null;
		}
		Locale locale = jstlWrapper.findLocale((LogicRequest) context);
		if (locale == null) {
			locale = Locale.getDefault();
		}
		DateFormat format = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
		try {
			Date date = format.parse(value);
			if (Date.class.isAssignableFrom(type)) {
				return date;
			} else {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				return calendar;
			}
		} catch (ParseException e) {
			throw new ConversionException("invalid_time", "Unable to parse string " + value, e);
		}
	}

	/**
	 * Returns the list of supported types
	 * 
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { Calendar.class, Date.class };
	}

}
