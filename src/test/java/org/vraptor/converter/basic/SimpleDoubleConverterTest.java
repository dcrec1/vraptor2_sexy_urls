package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

/**
 * Testunit for DoubleConverter.
 * 
 * @author Guilherme Silveira
 */
public class SimpleDoubleConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new SimpleDoubleConverter()
				.getSupportedTypes(), new Class[] { Double.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(500.1, new SimpleDoubleConverter().convert("500.1",
				Double.class, context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(-500.2, new SimpleDoubleConverter().convert("-500.2",
				Double.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(0.0, new SimpleDoubleConverter().convert("0",
				Double.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = createLogicRequest();
		try {
			new SimpleDoubleConverter().convert("500.1.2", Double.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testConvertsNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleDoubleConverter().convert(null, Double.class, context));
	}

	public void testConvertsEmpty() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleDoubleConverter().convert("", Double.class, context));
	}

}
