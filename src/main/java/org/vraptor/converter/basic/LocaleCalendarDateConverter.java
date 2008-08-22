package org.vraptor.converter.basic;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;
import org.vraptor.i18n.JstlWrapper;

/**
 * Locale based calendar/date converter. Uses the error key invalid_date if
 * unable to parse its information.
 * 
 * @author Guilherme Silveira
 */
public class LocaleCalendarDateConverter implements Converter {

	private final JstlWrapper jstlWrapper = new JstlWrapper();

	private static final Logger LOG = Logger.getLogger(LocaleCalendarDateConverter.class);

	public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
		if (value == null || value.equals("")) {
			return null;
		}
		Locale locale = jstlWrapper.findLocale((LogicRequest) context);
		if (locale == null) {
			locale = Locale.getDefault();
		}
		DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
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
			LocaleCalendarDateConverter.LOG.error("Unable to parse string " + value, e);
			throw new ConversionException("invalid_date", "Unable to parse string " + value, e);
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
