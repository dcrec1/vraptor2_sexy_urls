package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class SimpleShortConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new SimpleShortConverter()
				.getSupportedTypes(), new Class[] { Short.class }));
	}

	public void testConvertPositiveShort() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((short) 500, new SimpleShortConverter().convert("500",
				Short.class, context));
	}

	public void testConvertNegativeShort() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((short) -500, new SimpleShortConverter().convert("-500",
				Short.class, context));
	}

	public void testConvertZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((short) 0, new SimpleShortConverter().convert("0",
				Short.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = createLogicRequest();
		try {
			new SimpleShortConverter().convert("500.1", Short.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testConvertsNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleShortConverter().convert(null, Short.class,
				context));
	}

	public void testConvertsEmpty() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleShortConverter().convert("", Short.class, context));
	}
}
