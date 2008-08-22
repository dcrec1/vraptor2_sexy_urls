package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class PrimitiveLongConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new PrimitiveLongConverter()
				.getSupportedTypes(), new Class[] { long.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(500L, new PrimitiveLongConverter().convert("500",
				long.class, context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(-500L, new PrimitiveLongConverter().convert("-500",
				long.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(0L, new PrimitiveLongConverter().convert("0", long.class,
				context));
	}

	public void testConvertionError() {
		LogicRequest context = createLogicRequest();
		try {
			new PrimitiveLongConverter().convert("500.1", long.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

}
