package org.vraptor.converter;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.Conversion;
import org.vraptor.converter.basic.PrimitiveLongConverter;
import org.vraptor.converter.basic.StringConverter;

public class ConverterRepositoryTest extends AbstractTest {

	public static class OverridenStringConverter implements Converter {

		public Object convert(String value, Class<?> type, LogicRequest context)
				throws ConversionException {
			return String.format("{%s}", value);
		}

		public Class<?>[] getSupportedTypes() {
			return new Class[] { String.class };
		}

	}

	@SuppressWarnings("unused")
	@Conversion(OverridenStringConverter.class)
	private String overridenTest;

	private SimpleConverterManager repository = new SimpleConverterManager();

	public ConverterRepositoryTest() {
		repository.register(new StringConverter());
		repository.register(new PrimitiveLongConverter());
	}

	public void testOverridenConverter() throws ConversionException,
			SecurityException, NoSuchFieldException {
		LogicRequest context = createLogicRequest();
		assertTrue(repository.convert(new String[] { "test" }, "test",
				String.class, context, OverridenStringConverter.class).equals(
				"{test}"));
	}

	public void testValidArrayConverter() throws ConversionException {
		LogicRequest context = createLogicRequest();
		String[] value = new String[] { "10", "1" };
		long[] expected = new long[] { 10, 1 };
		assertTrue(Arrays.equals((long[]) repository.convert(value, "10,1",
				expected.getClass(), context, null), expected));
	}

	public void testTwoValidConverters() throws ConversionException {
		LogicRequest context = createLogicRequest();
		assertEquals("oi",repository.convert(new String[] { "oi" }, "oi", String.class,
				context, null));
		assertEquals(1000L,repository.convert(new String[] { "1000" }, "1000", long.class,
				context, null));
	}

	public void testTryUnknownArrayConverter() {
		LogicRequest context = createLogicRequest();
		ConverterRepositoryTest[] expected = new ConverterRepositoryTest[] {};
		try {
			repository.convert(new String[] { "10", "1" }, "10,1", expected
					.getClass(), context, null);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

	public void testUnknownConverter() {
		LogicRequest context = createLogicRequest();
		try {
			repository.convert(new String[] { "10", "1" }, "10,1",
					ConverterRepositoryTest.class, context, null);
			fail();
		} catch (ConversionException e) {
			// ok
		}
	}

}
