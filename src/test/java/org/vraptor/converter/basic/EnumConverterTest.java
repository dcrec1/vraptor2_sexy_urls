package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class EnumConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new EnumConverter().getSupportedTypes(),
				new Class[] { Enum.class }));
	}

	public void testConvertsByName() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(EnumExample.METHOD, new EnumConverter().convert("METHOD",
				EnumExample.class, context));
	}

	public void testConvertsEmptyValue() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new EnumConverter().convert("", EnumExample.class, context));
	}

	public void testConvertsByOrdinal() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(EnumExample.METHOD, new EnumConverter().convert("2",
				EnumExample.class, context));
	}

	public void testTriesToConvertInvalidOrdinalValue() {
		LogicRequest context = createLogicRequest();
		try {
			new EnumConverter().convert("5", EnumExample.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testTriesToConvertInvalidOrdinalNumber() {
		LogicRequest context = createLogicRequest();
		try {
			new EnumConverter().convert("5a", EnumExample.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testTriesToConvertInvalidName() {
		LogicRequest context = createLogicRequest();
		try {
			new EnumConverter().convert("Invalid", EnumExample.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}

	}

	public static enum EnumExample {
		FIELD, OTHER, METHOD;
	}

	public void testTriesToConvertNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new EnumConverter().convert(null, EnumExample.class, context));
	}

	public void testTriesToConvertEmpty() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new EnumConverter().convert("", EnumExample.class, context));
	}


}
