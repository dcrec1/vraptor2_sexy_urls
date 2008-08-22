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
public class SimpleIntegerConverterTest extends AbstractTest {

	public void testCanConvert() {
		assert Arrays.deepEquals(new SimpleIntegerConverter()
				.getSupportedTypes(), new Class[] { Integer.class });
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(500, new SimpleIntegerConverter().convert("500",
				Integer.class, context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(-500, new SimpleIntegerConverter().convert("-500",
				Integer.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(0, new SimpleIntegerConverter().convert("0",
				Integer.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = createLogicRequest();
		try {
			new SimpleIntegerConverter().convert("500.1", Integer.class,
					context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testConvertsNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleIntegerConverter().convert(null, Integer.class,
				context));
	}

	public void testConvertsEmpty() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleIntegerConverter().convert("", Integer.class,
				context));
	}
}
