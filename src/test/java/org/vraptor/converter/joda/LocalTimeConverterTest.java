package org.vraptor.converter.joda;

import org.joda.time.LocalTime;
import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class LocalTimeConverterTest extends AbstractTest {
    public void testCanConvert() {
        assertTrue(Arrays.deepEquals(new LocalTimeConverter().getSupportedTypes(),
                new Class[]{LocalTime.class}));
    }

    public void testConvertNull() throws ConversionException {
        LogicRequest context = getSetContext();
        Object convertedNull = new LocalTimeConverter().convert(null, LocalTime.class, context);
        Object convertedEmpty = new LocalTimeConverter().convert("", LocalTime.class, context);
        assertNull("Null", convertedNull);
        assertNull("Empty", convertedEmpty);
    }

    public void testConvert() throws ConversionException {
        LogicRequest context = getSetContext();
        LocalTime expectedTime = new LocalTime(11, 22);
        Object converted = new LocalTimeConverter().convert("11:22 am", LocalTime.class, context);
        assertEquals(expectedTime, converted);
    }

    public void testConvertFromCalendar() throws ConversionException {
        LogicRequest context = getSetContext();
        GregorianCalendar calendar = new GregorianCalendar(1970, 0, 1, 11, 22);
        LocalTime expectedTimeFromCalendar = LocalTime.fromCalendarFields(calendar);
        Object converted = new LocalTimeConverter().convert("11:22 am", LocalTime.class, context);
        assertEquals(expectedTimeFromCalendar, converted);
    }

    public void testConvertFromDate() throws ConversionException {
        LogicRequest context = getSetContext();
        Date date = new GregorianCalendar(1970, 0, 1, 11, 22).getTime();
        LocalTime expectedTimeFromDate = LocalTime.fromDateFields(date);
        Object converted = new LocalTimeConverter().convert("11:22 am", LocalTime.class, context);
        assertEquals(expectedTimeFromDate, converted);
    }

    private LogicRequest getSetContext() {
        LogicRequest context = createLogicRequest();
        context.getSessionContext().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", Locale.ENGLISH);
        return context;
    }

    public void testConvertionError() {
        LogicRequest context = getSetContext();
        try {
            new LocalTimeConverter().convert("XX:YY", LocalTime.class, context);
            fail();
        } catch (ConversionException e) {
            // ok
        }
    }
}
