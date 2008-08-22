package org.vraptor.converter.basic;

import java.util.Arrays;
import java.util.Locale;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;

/**
 * Testunit for DoubleConverter.
 * 
 * @author Guilherme Silveira
 */
public class LocaleConverterTest extends AbstractTest {

	public void testCanConvertLocale() {
		assertTrue(Arrays.deepEquals(new LocaleConverter().getSupportedTypes(),
				new Class[] { Locale.class }));
	}

	public void testConvertsLanguage() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(new Locale("en"), new LocaleConverter().convert("en",
				Locale.class, context));
	}

	public void testConvertsLanguageAndCountryWithUnderline()
			throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals(new Locale("en", "uk"), new LocaleConverter().convert(
				"en_uk", Locale.class, context));
	}

	public void testConvertsNull() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new LocaleConverter().convert(null, Locale.class, context));
	}

	public void testConvertsEmpty() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertNull(new LocaleConverter().convert("", Locale.class, context));
	}

}
