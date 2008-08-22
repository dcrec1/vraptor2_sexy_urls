package org.vraptor.converter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;

public class LocalePrimitiveDoubleConverterTest extends AbstractTest {

	public void testCanConvert() {
		List<Class<?>> list = Arrays.asList(new LocalePrimitiveDoubleConverter().getSupportedTypes());
		assertTrue(list.contains(double.class));
	}

	private LogicRequest getSetContext() {
		LogicRequest context = createLogicRequest();
		context.getSessionContext().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", new Locale("pt","BR"));
		return context;
	}

	public void testConvertsValidString() throws ConversionException {
		LogicRequest context = getSetContext();
		assertEquals(10.33, new LocalePrimitiveDoubleConverter().convert("10,33", double.class, context));
	}

	public void testConvertionErrorWithNaN() {
		LogicRequest context = getSetContext();
		try {
			new LocalePrimitiveDoubleConverter().convert("xx", double.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}	
	
	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(-500.2, new LocalePrimitiveDoubleConverter().convert("-500,2",
				double.class, context));
	}
	
	public void testConvertionErrorWithEmptyString() throws ConversionException {
		LogicRequest context = createLogicRequest();
		try {
			new LocalePrimitiveDoubleConverter().convert("", double.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}
	
	public void testConvertionErrorWithNullValue() throws ConversionException {
		LogicRequest context = createLogicRequest();
		try {
			new LocalePrimitiveDoubleConverter().convert(null, double.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(0d, new LocalePrimitiveDoubleConverter().convert("0",
				double.class, context));
	}

	
}
