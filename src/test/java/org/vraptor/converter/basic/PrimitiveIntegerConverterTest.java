package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

/**
 * Testunit for its converter.
 * 
 * @author Guilherme Silveira
 */
public class PrimitiveIntegerConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new PrimitiveIntConverter()
				.getSupportedTypes(), new Class[] { int.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(500, new PrimitiveIntConverter().convert("500", int.class,
				context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(-500, new PrimitiveIntConverter().convert("-500",
				int.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(0, new PrimitiveIntConverter().convert("0", int.class,
				context));
	}

	public void testConvertionError() throws ConversionException {
		LogicRequest context = createLogicRequest();
		try {
			new PrimitiveIntConverter().convert("500.1", int.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}
}
