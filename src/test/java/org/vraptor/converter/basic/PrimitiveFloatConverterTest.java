package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class PrimitiveFloatConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new PrimitiveFloatConverter()
				.getSupportedTypes(), new Class[] { float.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(500.0f, new PrimitiveFloatConverter().convert("500.0",
				float.class, context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(-500.1f, new PrimitiveFloatConverter().convert("-500.1",
				float.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(0.0f, new PrimitiveFloatConverter().convert("0",
				float.class, context));
	}

	public void testConvertionError() throws ConversionException {
		LogicRequest context = createLogicRequest();
		try {
			new PrimitiveFloatConverter().convert("500a.bbsd1", float.class,
					context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

}
