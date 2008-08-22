package org.vraptor.converter.joda;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.TimeOfDay;
import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class TimeOfDayConverterTest extends AbstractTest {
	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new TimeOfDayConverter().getSupportedTypes(),
				new Class[] { TimeOfDay.class }));
	}

	public void testConvertNull() throws ConversionException {
		LogicRequest context = getSetContext();
		Object convertedNull = new TimeOfDayConverter().convert(null, TimeOfDay.class, context);
		Object convertedEmpty = new TimeOfDayConverter().convert("", TimeOfDay.class, context);
		assertNull("Null", convertedNull);
		assertNull("Empty", convertedEmpty);
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = getSetContext();
		TimeOfDay expectedTime = new TimeOfDay(11, 22);
		Object converted = new TimeOfDayConverter().convert("11:22 am", TimeOfDay.class, context);
		assertEquals(expectedTime, converted);
	}

	public void testConvertFromCalendar() throws ConversionException {
		LogicRequest context = getSetContext();
		GregorianCalendar calendar = new GregorianCalendar(1970, 0, 1, 11, 22);
		TimeOfDay expectedTimeFromCalendar = TimeOfDay.fromCalendarFields(calendar);
		Object converted = new TimeOfDayConverter().convert("11:22 am", TimeOfDay.class, context);
		assertEquals(expectedTimeFromCalendar, converted);
	}

	public void testConvertFromDate() throws ConversionException {
		LogicRequest context = getSetContext();
		Date date = new GregorianCalendar(1970, 0, 1, 11, 22).getTime();
		TimeOfDay expectedTimeFromDate = TimeOfDay.fromDateFields(date);
		Object converted = new TimeOfDayConverter().convert("11:22 am", TimeOfDay.class, context);
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
			new TimeOfDayConverter().convert("XX:YY", TimeOfDay.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}
}
