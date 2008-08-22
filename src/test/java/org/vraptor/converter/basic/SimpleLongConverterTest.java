package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class SimpleLongConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new SimpleLongConverter()
				.getSupportedTypes(), new Class[] { Long.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(500L, new SimpleLongConverter().convert("500", Long.class,
				context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(-500L, new SimpleLongConverter().convert("-500",
				Long.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(0L, new SimpleLongConverter().convert("0", Long.class,
				context));
	}

	public void testConvertionError() {
		LogicRequest context = createLogicRequest();
		try {
			new SimpleLongConverter().convert("500.1", Long.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testConvertsNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleLongConverter().convert(null, Long.class, context));
	}

	public void testConvertsEmpty() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleLongConverter().convert("", Long.class, context));
	}

}
