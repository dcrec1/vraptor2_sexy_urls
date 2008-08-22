package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class PrimitiveCharConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new PrimitiveCharConverter()
				.getSupportedTypes(), new Class[] { char.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals('5', new PrimitiveCharConverter().convert("5", char.class,
				context));
	}

	public void testConvertUTF8() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals('\u5377', new PrimitiveCharConverter().convert("\u5377",
				char.class, context));
	}

	public void testConvertionError() throws ConversionException {
		LogicRequest context = createLogicRequest();
		try {
			new PrimitiveCharConverter().convert("50", char.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

}
