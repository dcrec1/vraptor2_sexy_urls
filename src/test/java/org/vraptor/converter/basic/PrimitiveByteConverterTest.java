package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

/**
 * Testunit for its converter
 * 
 * @author Guilherme Silveira
 */
public class PrimitiveByteConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new PrimitiveByteConverter()
				.getSupportedTypes(), new Class[] { byte.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((byte) 5, new PrimitiveByteConverter().convert("5",
				byte.class, context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((byte) -5, new PrimitiveByteConverter().convert("-5",
				byte.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((byte) 0, new PrimitiveByteConverter().convert("0",
				byte.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = createLogicRequest();
		try {
			new PrimitiveByteConverter().convert("500.1", byte.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}

	}
}
