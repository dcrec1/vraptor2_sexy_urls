package org.vraptor.converter.joda;

import org.joda.time.LocalDate;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;
import org.vraptor.converter.basic.LocaleCalendarDateConverter;

import java.util.Calendar;

/**
 * <p/>
 * Locale based joda-time {@link org.joda.time.LocalDate} converter. Uses the error key
 * invalid_date if unable to parse its information.
 * </p>
 * <p/>
 * Same semantics as {@link org.vraptor.converter.LocaleCalendarTimeConverter}.
 * </p>
 *
 * @author Fabio Kung
 */
public class LocalDateConverter implements Converter {
    private final Converter internal = new LocaleCalendarDateConverter();

    public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
        if (value == null || value.equals("")) {
            return null;
        }

        Calendar converted = (Calendar) internal.convert(value, Calendar.class, context);
        try {
            return LocalDate.fromCalendarFields(converted);
        } catch (Exception e) {
            throw new ConversionException("invalid_date", "Unable to parse string " + value, e);
        }
    }

    public Class<?>[] getSupportedTypes() {
        return new Class<?>[]{LocalDate.class};
    }
}
