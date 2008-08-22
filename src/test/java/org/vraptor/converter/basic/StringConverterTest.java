package org.vraptor.converter.basic;

import org.vraptor.AbstractTest;
import org.vraptor.converter.ConversionException;

public class StringConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertTrue(new StringConverter().canConvert(String.class));
	}

	public void testConvert() throws ConversionException {
		assertEquals("asdf", new StringConverter().convert("asdf",
				String.class, createLogicRequest()));
	}

	public void testConvertsNull() throws ConversionException {
		assertNull(new StringConverter().convert(null, String.class,
				createLogicRequest()));
	}

}
