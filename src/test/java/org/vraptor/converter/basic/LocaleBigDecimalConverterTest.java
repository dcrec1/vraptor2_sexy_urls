package org.vraptor.converter.basic;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class LocaleBigDecimalConverterTest extends AbstractTest {

	public void testCanConvert() {
		List<Class<?>> list = Arrays.asList(new LocaleBigDecimalConverter().getSupportedTypes());
		assertTrue(list.contains(BigDecimal.class));
	}

	private LogicRequest getSetContext() {
		LogicRequest context = createLogicRequest();
		context.getSessionContext().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", new Locale("pt","BR"));
		return context;
	}

	public void testConvertsValidString() throws ConversionException {
		LogicRequest context = getSetContext();
		assertEquals(new BigDecimal("10.33"), new LocaleBigDecimalConverter().convert("10,33", BigDecimal.class, context));
	}

	public void testConvertsNullStringToNull() throws ConversionException {
		LogicRequest context = getSetContext();
		assertNull(new LocaleBigDecimalConverter().convert(null, BigDecimal.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = getSetContext();
		try {
			new LocaleBigDecimalConverter().convert("xx", BigDecimal.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}	
}
