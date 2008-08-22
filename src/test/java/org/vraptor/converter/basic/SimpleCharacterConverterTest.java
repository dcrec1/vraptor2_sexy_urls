package org.vraptor.converter.basic;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

/**
 * Testunit for its converter.
 * 
 * @author Guilherme Silveira
 */
public class SimpleCharacterConverterTest extends AbstractTest {

	public void testCanConvert() {
		assertEquals(new SimpleCharacterConverter().getSupportedTypes()[0],
				Character.class);
	}

	public void testConvert() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals((char) '5', new SimpleCharacterConverter().convert("5",
				Character.class, context));
	}

	public void testConvertionError() {
		LogicRequest context = createLogicRequest();
		try {
			new SimpleCharacterConverter().convert("500.1", Character.class,
					context);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testConvertsNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleCharacterConverter().convert(null,
				Character.class, context));
	}

	public void testConvertsEmpty() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new SimpleCharacterConverter().convert("", Character.class,
				context));
	}
}
