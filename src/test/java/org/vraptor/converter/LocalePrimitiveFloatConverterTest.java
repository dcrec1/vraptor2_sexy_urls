package org.vraptor.converter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;

public class LocalePrimitiveFloatConverterTest extends AbstractTest {

	public void testCanConvert() {
		List<Class<?>> list = Arrays.asList(new LocalePrimitiveFloatConverter().getSupportedTypes());
		assertTrue(list.contains(float.class));
	}

	private LogicRequest getSetContext() {
		LogicRequest context = createLogicRequest();
		context.getSessionContext().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", new Locale("pt","BR"));
		return context;
	}

	public void testConvertsValidString() throws ConversionException {
		LogicRequest context = getSetContext();
		assertEquals(10.33f, new LocalePrimitiveFloatConverter().convert("10,33", float.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = getSetContext();
		try {
			new LocalePrimitiveFloatConverter().convert("xx", float.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}	
	
	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(-500.2f, new LocalePrimitiveFloatConverter().convert("-500,2",
				float.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(0f, new LocalePrimitiveFloatConverter().convert("0",
				float.class, context));
	}
	
	public void testConvertionErrorWithEmptyString() throws ConversionException {
		LogicRequest context = createLogicRequest();
		try {
			new LocalePrimitiveFloatConverter().convert("", double.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}
	
	public void testConvertionErrorWithNullValue() throws ConversionException {
		LogicRequest context = createLogicRequest();
		try {
			new LocalePrimitiveFloatConverter().convert(null, double.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}
}
