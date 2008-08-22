package org.vraptor.converter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;

public class LocaleCalendarTimeConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new LocaleCalendarTimeConverter().getSupportedTypes(), new Class[] {
				Calendar.class, Date.class }));
	}

	public void testConvertToCalendar() throws ConversionException {
		LogicRequest context = getSetContext();
		GregorianCalendar calendar = new GregorianCalendar(1970, 0, 1, 11, 22);
		assertEquals(calendar, new LocaleCalendarTimeConverter().convert("11:22 am", Calendar.class, context));
	}

	public void testConvertToDate() throws ConversionException {
		LogicRequest context = getSetContext();
		Date date = new GregorianCalendar(1970, 0, 1, 11, 22).getTime();
		assertEquals(date, new LocaleCalendarTimeConverter().convert("11:22 am", Date.class, context));
	}

	public void testConvertsNullStringToNull() throws ConversionException {
		LogicRequest context = getSetContext();
		assertNull(new LocaleCalendarTimeConverter().convert(null, Calendar.class, context));
	}

	public void testConvertsNullStringToNullDate() throws ConversionException {
		LogicRequest context = getSetContext();
		assertNull(new LocaleCalendarTimeConverter().convert(null, Date.class, context));
	}

	private LogicRequest getSetContext() {
		LogicRequest context = createLogicRequest();
		context.getSessionContext().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", Locale.ENGLISH);
		return context;
	}

	public void testConvertionError() {
		LogicRequest context = getSetContext();
		try {
			new LocaleCalendarTimeConverter().convert("XX:YY", Calendar.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}
}
