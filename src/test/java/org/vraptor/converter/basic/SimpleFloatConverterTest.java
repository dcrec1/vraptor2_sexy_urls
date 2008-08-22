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
public class SimpleFloatConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new SimpleFloatConverter()
				.getSupportedTypes(), new Class[] { Float.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(500.1f, new SimpleFloatConverter().convert("500.1",
				Float.class, context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(-500.2f, new SimpleFloatConverter().convert("-500.2",
				Float.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(0.0f, new SimpleFloatConverter().convert("0", Float.class,
				context));
	}

	public void testConvertionError() {
		LogicRequest context = createLogicRequest();
		try {
			new SimpleFloatConverter().convert("500.1.2", Float.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testConvertsNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleFloatConverter().convert(null, Float.class,
				context));
	}

	public void testConvertsEmpty() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleFloatConverter().convert("", Float.class, context));
	}

}
