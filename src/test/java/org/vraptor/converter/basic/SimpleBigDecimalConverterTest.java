package org.vraptor.converter.basic;

import java.math.BigDecimal;
import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

/**
 * Testunit for BigDecimalConverter.
 * 
 * @author Ricardo A. F. da Cruz
 */
public class SimpleBigDecimalConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(Arrays.deepEquals(new SimpleBigDecimalConverter()
				.getSupportedTypes(), new Class[] { BigDecimal.class }));
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(new BigDecimal("500.1"), new SimpleBigDecimalConverter().convert("500.1",
				BigDecimal.class, context));
	}

	public void testConvertsNegative() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(new BigDecimal("-500.2"), new SimpleBigDecimalConverter().convert("-500.2",
				BigDecimal.class, context));
	}

	public void testConvertsZero() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(new BigDecimal("0"), new SimpleBigDecimalConverter().convert("0",
				BigDecimal.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = createLogicRequest();
		try {
			new SimpleBigDecimalConverter().convert("500.1.2", BigDecimal.class, context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testConvertsNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleBigDecimalConverter().convert(null, BigDecimal.class, context));
	}

	public void testConvertsEmpty() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleBigDecimalConverter().convert("", BigDecimal.class, context));
	}

}
