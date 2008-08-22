package org.vraptor.converter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;

public class LocaleDoubleConverterTest extends AbstractTest {

	public void testCanConvert() {
		List<Class<?>> list = Arrays.asList(new LocaleDoubleConverter().getSupportedTypes());
		assertTrue(list.contains(Double.class));
	}

	private LogicRequest getSetContext() {
		LogicRequest context = createLogicRequest();
		context.getSessionContext().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", new Locale("pt","BR"));
		return context;
	}

	public void testConvertsValidString() throws ConversionException {
		LogicRequest context = getSetContext();
		assertEquals(new Double(10.33), new LocaleDoubleConverter().convert("10,33", Double.class, context));
	}

	public void testConvertsNullStringToNull() throws ConversionException {
		LogicRequest context = getSetContext();
		assertNull(new LocaleDoubleConverter().convert(null, Double.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = getSetContext();
		try {
			new LocaleDoubleConverter().convert("xx", Double.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}	
}
