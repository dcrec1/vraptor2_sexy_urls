package org.vraptor.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

/**
 * Fixed Locale based calendar converter. Uses the error key invalid_date if
 * unable to parse its information.
 * 
 * @author Guilherme Silveira
 */
public class FixedPatternCalendarConverter implements Converter {

	private static final Logger logger = Logger.getLogger(FixedPatternCalendarConverter.class);

	private String pattern;

	/**
	 * Simple constructor with pattern
	 * 
	 * @param pattern
	 *            pattern
	 */
	public FixedPatternCalendarConverter(String pattern) {
		super();
		this.pattern = pattern;
	}

	/**
	 * 
	 * @see org.vraptor.converter.Converter#convert(java.lang.String,
	 *      java.lang.Class, org.vraptor.scope.LogicRequest)
	 */
	public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
		DateFormat format = new SimpleDateFormat(this.pattern);
		Calendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(format.parse(value));
		} catch (ParseException e) {
			throw new ConversionException("invalid_value", "Unable to parse string " + value, e);
		}
		if (type.equals(Date.class)) {
			return calendar.getTime();
		}
		return calendar;
	}

	public Class<?>[] getSupportedTypes() {
		return new Class[] { Calendar.class, Date.class };
	}

}
