package org.vraptor.converter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.basic.LocaleCalendarDateConverter;

public class LocaleCalendarDateConverterTest extends AbstractTest {

	public void testCanConvert() {
		List<Class<?>> list = Arrays.asList(new LocaleCalendarDateConverter()
				.getSupportedTypes());
		assertTrue(list.contains(Calendar.class));
		assertTrue(list.contains(Date.class));
	}

	public void testConvertsValidString() throws ConversionException {
		LogicRequest context = getSetContext();
		GregorianCalendar calendar = new GregorianCalendar(1970, 0, 1);
		assertEquals(calendar, new LocaleCalendarDateConverter().convert(
				"1/1/1970", Calendar.class, context));
	}

	public void testConvertsNullStringToNull() throws ConversionException {
		LogicRequest context = getSetContext();
		assertNull(new LocaleCalendarDateConverter().convert(null,
				Calendar.class, context));
	}

	private LogicRequest getSetContext() {
		LogicRequest context = createLogicRequest();
		context.getSessionContext().setAttribute(
				"javax.servlet.jsp.jstl.fmt.locale.session", Locale.ENGLISH);
		return context;
	}

	public void testConvertionError() {
		LogicRequest context = getSetContext();
		try {
			new LocaleCalendarDateConverter().convert("XX:YY", Calendar.class,
					context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testConvertsValidStringWithDate() throws ConversionException {
		LogicRequest context = getSetContext();
		Date calendar = new GregorianCalendar(1970, 0, 1).getTime();
		assertEquals(calendar, new LocaleCalendarDateConverter().convert(
				"1/1/1970", Date.class, context));
	}

	public void testConvertsNullStringToNullWithDate()
			throws ConversionException {
		LogicRequest context = getSetContext();
		assertNull(new LocaleCalendarDateConverter().convert(null, Date.class,
				context));
	}

	public void testConvertionErrorWithDate() {
		LogicRequest context = getSetContext();
		try {
			new LocaleCalendarDateConverter().convert("XX:YY", Date.class,
					context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}
}
