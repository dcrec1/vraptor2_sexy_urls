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
public class PrimitiveShortConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new PrimitiveShortConverter()
				.getSupportedTypes(), new Class[] { short.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((short) 500, new PrimitiveShortConverter().convert("500",
				short.class, context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((short) -500, new PrimitiveShortConverter().convert(
				"-500", short.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((short) 0, new PrimitiveShortConverter().convert("0",
				short.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = createLogicRequest();
		try {
			new PrimitiveShortConverter()
					.convert("500.1", short.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}
}
