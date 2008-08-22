package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class SimpleByteConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new SimpleByteConverter()
				.getSupportedTypes(), new Class[] { Byte.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((byte) 5, new SimpleByteConverter().convert("5",
				Byte.class, context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((byte) -5, new SimpleByteConverter().convert("-5",
				Byte.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((byte) 0, new SimpleByteConverter().convert("0",
				Byte.class, context));
	}

	public void testConvertionErrorTest() {
		LogicRequest context = createLogicRequest();
		try {
			new SimpleByteConverter().convert("500.1", Byte.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testConvertsNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleByteConverter().convert(null, Byte.class,
				context));
	}

	public void testConvertsEmpty() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleByteConverter().convert("", Byte.class, context));
	}
}
