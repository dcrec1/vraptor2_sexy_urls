package org.vraptor.introspector;

import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.vraptor.LogicRequest;
import org.vraptor.annotations.Conversion;
import org.vraptor.annotations.Read;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Tests for the read parameter class.
 * 
 * @author Guilherme Silveira
 */
public class FieldReadParameterTest extends TestCase {

	public class OverridenStringConverter implements Converter {

		public Object convert(String value, Class<?> type, LogicRequest context) throws ConversionException {
			return String.format("{%s}", value);
		}

		public Class<?>[] getSupportedTypes() {
			return new Class[] { String.class };
		}

	}

	@Read
	@SuppressWarnings("unused")
	private String test1;

	@Read(key = "test", create = true)
	@Conversion(OverridenStringConverter.class)
	@SuppressWarnings("unused")
	private String test2;

	private FieldReadParameter param = new FieldReadParameter(getField("test1"));

	private FieldReadParameter param2 = new FieldReadParameter(getField("test2"));

	public void testKey() {
		assertEquals("test1", param.getKey());
		assertEquals("test", param2.getKey());
	}

	private FieldAnnotation<Read> getField(String name) {
		Field f = null;
		try {
			f = FieldReadParameterTest.class.getDeclaredField(name);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return new FieldAnnotation<Read>(f.getAnnotation(Read.class), f);
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
