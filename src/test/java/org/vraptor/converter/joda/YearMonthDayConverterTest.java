package org.vraptor.converter.joda;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.YearMonthDay;
import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class YearMonthDayConverterTest extends AbstractTest {
	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new YearMonthDayConverter().getSupportedTypes(),
				new Class[] { YearMonthDay.class }));
	}

	public void testConvertNull() throws ConversionException {
		LogicRequest context = getSetContext();
		Object convertedNull = new YearMonthDayConverter().convert(null, YearMonthDay.class, context);
		Object convertedEmpty = new YearMonthDayConverter().convert("", YearMonthDay.class, context);
		assertNull("Null", convertedNull);
		assertNull("Empty", convertedEmpty);
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = getSetContext();
		YearMonthDay expected = new YearMonthDay(1970, 1, 1);
		assertEquals(expected, new YearMonthDayConverter().convert("1/1/1970", YearMonthDay.class, context));
	}

	public void testConvertFromCalendar() throws ConversionException {
		LogicRequest context = getSetContext();
		GregorianCalendar calendar = new GregorianCalendar(1970, 0, 1);
		YearMonthDay expectedFromCalendar = YearMonthDay.fromCalendarFields(calendar);
		Object converted = new YearMonthDayConverter().convert("1/1/1970", YearMonthDay.class, context);
		assertEquals(expectedFromCalendar, converted);
	}

	public void testConvertFromDate() throws ConversionException {
		LogicRequest context = getSetContext();
		Date date = new GregorianCalendar(1970, 0, 1).getTime();
		YearMonthDay expectedFromDate = YearMonthDay.fromDateFields(date);
		Object converted = new YearMonthDayConverter().convert("1/1/1970", YearMonthDay.class, context);
		assertEquals(expectedFromDate, converted);
	}

	private LogicRequest getSetContext() {
		LogicRequest context = createLogicRequest();
		context.getSessionContext().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", Locale.ENGLISH);
		return context;
	}

	public void testConvertionError() {
		LogicRequest context = getSetContext();
		try {
			new YearMonthDayConverter().convert("XX:YY", YearMonthDay.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

}
