package org.vraptor.config;

import junit.framework.TestCase;

import org.vraptor.LogicRequest;
import org.vraptor.config.xml.ConverterConfigConverter;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ConfigConverterConverterTest extends TestCase {

	public void testCanConvert() {
		assertTrue(new ConverterConfigConverter()
				.canConvert(ConverterConfig.class));
	}

	public void testMarshall() {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ConverterConfigConverter());
		xstream.alias("converter", ConverterConfig.class);
		assertEquals(String.format("<converter>%s</converter>",
				TestConverter.class.getName()), xstream
				.toXML(new ConverterConfig(TestConverter.class)));
	}

	public void testUnmarshallType() {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ConverterConfigConverter());
		xstream.alias("converter", ConverterConfig.class);
		assertEquals(ConverterConfig.class, xstream.fromXML(
				String.format("<converter>%s</converter>", TestConverter.class
						.getName())).getClass());
	}

	public void testUnmarshall() {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ConverterConfigConverter());
		xstream.alias("converter", ConverterConfig.class);
		ConverterConfig converter = (ConverterConfig) xstream.fromXML(String
				.format("<converter>%s</converter>", TestConverter.class
						.getName()));
		assertEquals(TestConverter.class, converter.getConverterClass());
	}

	class TestConverter implements Converter {

		public Object convert(String value, Class<?> type, LogicRequest context)
				throws ConversionException {
			return null;
		}

		public Class<?>[] getSupportedTypes() {
			return null;
		}

	}

}
