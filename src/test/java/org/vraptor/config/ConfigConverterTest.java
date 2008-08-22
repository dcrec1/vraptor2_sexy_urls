package org.vraptor.config;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;
import org.vraptor.converter.ConverterManager;

public class ConfigConverterTest extends AbstractTest {

	public void testRegistersTheConverter() {
		ConverterConfig converter = new ConverterConfig(TestConverter.class);
		Mock mock = mock(ConverterManager.class);
		mock.expects(once()).method("register");
		ConverterManager manager = (ConverterManager) mock.proxy();
		converter.register(manager);
	}

	public void testRegistersInvalidConverter() {
		ConverterConfig converter = new ConverterConfig(
				InvalidConstructor.class);
		Mock mock = mock(ConverterManager.class);
		mock.expects(never()).method("register");
		ConverterManager manager = (ConverterManager) mock.proxy();
		converter.register(manager);
	}

	public static class TestConverter implements Converter {

		public Object convert(String value, Class<?> type, LogicRequest context)
				throws ConversionException {
			return null;
		}

		public Class<?>[] getSupportedTypes() {
			return null;
		}

	}

	private static class InvalidConstructor implements Converter {

		public Object convert(String value, Class<?> type, LogicRequest context)
				throws ConversionException {
			return null;
		}

		public Class<?>[] getSupportedTypes() {
			return null;
		}

	}

}
