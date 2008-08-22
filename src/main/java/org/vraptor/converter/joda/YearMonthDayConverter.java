package org.vraptor.converter.joda;

import org.joda.time.YearMonthDay;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;
import org.vraptor.converter.basic.LocaleCalendarDateConverter;

import java.util.Calendar;

/**
 * <p>
 * Locale based joda-time {@link YearMonthDay} converter. Uses the error key
 * invalid_date if unable to parse its information.
 * </p>
 * <p>
 * Same semantics as {@link LocaleCalendarDateConverter}.
 * </p>
 * 
 * @author Fabio Kung
 */
@Deprecated
public class YearMonthDayConverter implements Converter {
    private Converter internal = new LocaleCalendarDateConverter();

    public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
		if (value == null || value.equals("")) {
			return null;
		}
		
		Calendar converted = (Calendar) internal.convert(value, Calendar.class, context);
        YearMonthDay date;
        try {
            date = YearMonthDay.fromCalendarFields(converted);
        } catch (Exception e) {
            throw new ConversionException("invalid_date", "Unable to parse string " + value, e);
        }
        return date;
    }

    public Class<?>[] getSupportedTypes() {
        return new Class<?>[]{YearMonthDay.class};
    }
}
