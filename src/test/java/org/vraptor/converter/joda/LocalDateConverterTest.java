package org.vraptor.converter.joda;

import org.joda.time.LocalDate;
import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class LocalDateConverterTest extends AbstractTest {
	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new LocalDateConverter().getSupportedTypes(),
				new Class[] { LocalDate.class }));
	}

	public void testConvertNull() throws ConversionException {
		LogicRequest context = getSetContext();
		Object convertedNull = new LocalDateConverter().convert(null, LocalDate.class, context);
		Object convertedEmpty = new LocalDateConverter().convert("", LocalDate.class, context);
		assertNull("Null", convertedNull);
		assertNull("Empty", convertedEmpty);
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = getSetContext();
		LocalDate expected = new LocalDate(1970, 1, 1);
		assertEquals(expected, new LocalDateConverter().convert("1/1/1970", LocalDate.class, context));
	}

	public void testConvertFromCalendar() throws ConversionException {
		LogicRequest context = getSetContext();
		GregorianCalendar calendar = new GregorianCalendar(1970, 0, 1);
		LocalDate expectedFromCalendar = LocalDate.fromCalendarFields(calendar);
		Object converted = new LocalDateConverter().convert("1/1/1970", LocalDate.class, context);
		assertEquals(expectedFromCalendar, converted);
	}

	public void testConvertFromDate() throws ConversionException {
		LogicRequest context = getSetContext();
		Date date = new GregorianCalendar(1970, 0, 1).getTime();
		LocalDate expectedFromDate = LocalDate.fromDateFields(date);
		Object converted = new LocalDateConverter().convert("1/1/1970", LocalDate.class, context);
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
			new LocalDateConverter().convert("XX:YY", LocalDate.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

}
