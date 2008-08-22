package org.vraptor.converter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;

public class FixedPatternCalendarConverterTest extends AbstractTest {

	private final String pattern = "dd/MM/yyyy";

	private final String realValue = "18/09/1981", wrongValue = "18091981";

	private final Calendar expectedValue = new GregorianCalendar(1981, 8, 18);

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new FixedPatternCalendarConverter(pattern).getSupportedTypes(), new Class[] {
				Calendar.class, Date.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		Calendar value = (Calendar) new FixedPatternCalendarConverter(pattern).convert(this.realValue, Calendar.class,
				context);
		assertEquals(expectedValue, value);
	}

	public void testWrongConvertion() {
		LogicRequest context = createLogicRequest();
		try {
			new FixedPatternCalendarConverter(pattern).convert(this.wrongValue, Calendar.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

}
