package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class SimpleBooleanConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new SimpleBooleanConverter().getSupportedTypes(), new Class[] { Boolean.class }));
	}

	public void testConvertsLowercaseTrue() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(Boolean.TRUE, new SimpleBooleanConverter().convert("true", Boolean.class, context));
	}

	public void testConvertsUppercaseTrue() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(Boolean.TRUE, new SimpleBooleanConverter().convert("TRUE", Boolean.class, context));
	}

	public void testConvertsLowercaseFalse() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(Boolean.FALSE, new SimpleBooleanConverter().convert("false", Boolean.class, context));
	}

	public void testConvertsUppercaseFalse() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(Boolean.FALSE, new SimpleBooleanConverter().convert("FALSE", Boolean.class, context));
	}

	public void testConvertsNullToNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleBooleanConverter().convert(null, Boolean.class, context));
	}
	public void testConvertsEmptyStringToNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleBooleanConverter().convert("", Boolean.class, context));
	}
}
