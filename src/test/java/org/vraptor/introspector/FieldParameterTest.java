package org.vraptor.introspector;

import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.vraptor.LogicRequest;
import org.vraptor.annotations.Conversion;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Tests for the read parameter class.
 * 
 * @author Guilherme Silveira
 */
public class FieldParameterTest extends TestCase {

	public class OverridenStringConverter implements Converter {

		public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
			return String.format("{%s}", value);
		}

		public Class<?>[] getSupportedTypes() {
			return new Class[] { String.class };
		}

	}

	@org.vraptor.annotations.Parameter
	@SuppressWarnings("unused")
	private String test1;

	@org.vraptor.annotations.Parameter(key = "test", create = true)
	@Conversion(OverridenStringConverter.class)
	@SuppressWarnings("unused")
	private String test2;

	private FieldParameter param = new FieldParameter(getField("test1"));

	private FieldParameter param2 = new FieldParameter(getField("test2"));

	public void testKey() {
		assertEquals("test1", param.getKey());
		assertEquals("test", param2.getKey());
	}

	private FieldAnnotation<org.vraptor.annotations.Parameter> getField(String name) {
		Field f = null;
		try {
			f = FieldParameterTest.class.getDeclaredField(name);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return new FieldAnnotation<org.vraptor.annotations.Parameter>(f.getAnnotation(org.vraptor.annotations.Parameter.class), f);
	}

	public void testShouldInstantiateInternal() {
		assertFalse(param.mightCreate());
	}

	public void testShouldntInstantiateInternal() {
		assertTrue(param2.mightCreate());
	}

	public void testNoOverridenConverter() {
		assertNull(param.getOverridenConverter());
	}

	public void testOverridenConverter() {
		assertEquals(OverridenStringConverter.class, param2.getOverridenConverter());
	}

}
