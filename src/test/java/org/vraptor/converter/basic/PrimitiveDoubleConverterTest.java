package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class PrimitiveDoubleConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new PrimitiveDoubleConverter()
				.getSupportedTypes(), new Class[] { double.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(500.3, new PrimitiveDoubleConverter().convert("500.3",
				double.class, context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(-500.2, new PrimitiveDoubleConverter().convert("-500.2",
				double.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(0d, new PrimitiveDoubleConverter().convert("0",
				double.class, context));
	}

	public void testConvertionError() throws ConversionException {
		LogicRequest context = createLogicRequest();
		try {
			new PrimitiveDoubleConverter().convert("500.adsdsa.21",
					double.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}

	}

}
