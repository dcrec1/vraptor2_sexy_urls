package org.vraptor.converter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;

public class LocaleFloatConverterTest extends AbstractTest {

	public void testCanConvert() {
		List<Class<?>> list = Arrays.asList(new LocaleFloatConverter().getSupportedTypes());
		assertTrue(list.contains(Float.class));
	}

	private LogicRequest getSetContext() {
		LogicRequest context = createLogicRequest();
		context.getSessionContext().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", new Locale("pt","BR"));
		return context;
	}

	public void testConvertsValidString() throws ConversionException {
		LogicRequest context = getSetContext();
		assertEquals(new Float(10.33), new LocaleFloatConverter().convert("10,33", Float.class, context));
	}

	public void testConvertsNullStringToNull() throws ConversionException {
		LogicRequest context = getSetContext();
		assertNull(new LocaleFloatConverter().convert(null, Float.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = getSetContext();
		try {
			new LocaleFloatConverter().convert("xx", Float.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}	
}
