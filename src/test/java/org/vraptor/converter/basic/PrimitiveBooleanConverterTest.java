package org.vraptor.converter.basic;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

public class PrimitiveBooleanConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new PrimitiveBooleanConverter()
				.getSupportedTypes(), new Class[] { boolean.class }));
	}

	public void testConvertLowercaseTrue() throws ConversionException {
		LogicRequest context = createLogicRequest();
		PrimitiveBooleanConverter converter = new PrimitiveBooleanConverter();
		assertEquals(Boolean.TRUE, converter.convert("true", Boolean.class,
				context));
	}

	public void testConvertUppercaseTrue() throws ConversionException {
		LogicRequest context = createLogicRequest();
		PrimitiveBooleanConverter converter = new PrimitiveBooleanConverter();
		assertEquals(Boolean.TRUE, converter.convert("TRUE", Boolean.class,
				context));
	}

	public void testConvertLowercaseFalse() throws ConversionException {
		LogicRequest context = createLogicRequest();
		PrimitiveBooleanConverter converter = new PrimitiveBooleanConverter();
		assertEquals(Boolean.FALSE, converter.convert("false", Boolean.class,
				context));
	}

	public void testConvertUppercaseFalse() throws ConversionException {
		LogicRequest context = createLogicRequest();
		PrimitiveBooleanConverter converter = new PrimitiveBooleanConverter();
		assertEquals(Boolean.FALSE, converter.convert("FALSE", Boolean.class,
				context));
	}

	public void testConvertsNullToFalse() throws ConversionException {
		LogicRequest context = createLogicRequest();
		PrimitiveBooleanConverter converter = new PrimitiveBooleanConverter();
		assertEquals(Boolean.FALSE, converter.convert(null, Boolean.class,
				context));
	}


	public void testConvertsEmptyToFalse() throws ConversionException {
		LogicRequest context = createLogicRequest();
		PrimitiveBooleanConverter converter = new PrimitiveBooleanConverter();
		assertEquals(Boolean.FALSE, converter.convert("", Boolean.class,
				context));
	}

}
