package org.vraptor.converter.joda;

import org.joda.time.LocalTime;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;
import org.vraptor.converter.LocaleCalendarTimeConverter;

import java.util.Calendar;

/**
 * <p/>
 * Locale based joda-time {@link org.joda.time.LocalTime} converter. Uses the error key
 * invalid_time if unable to parse its information.
 * </p>
 * <p/>
 * Same semantics as {@link org.vraptor.converter.LocaleCalendarTimeConverter}.
 * </p>
 *
 * @author Fabio Kung
 */
public class LocalTimeConverter implements Converter {
    private Converter internal = new LocaleCalendarTimeConverter();

    public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
        if (value == null || value.equals("")) {
            return null;
        }

        Calendar converted = (Calendar) internal.convert(value, Calendar.class, context);
        try {
            return LocalTime.fromCalendarFields(converted);
        } catch (Exception e) {
            throw new ConversionException("invalid_time", "Unable to parse string " + value, e);
        }
    }

    public Class<?>[] getSupportedTypes() {
        return new Class<?>[]{LocalTime.class};
    }
}
