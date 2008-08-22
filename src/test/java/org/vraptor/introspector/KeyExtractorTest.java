package org.vraptor.introspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Read;
import org.vraptor.component.FieldAnnotation;

/**
 * Tests key extraction from annotated fields.
 * 
 * @author Guilherme Silveira
 */
public class KeyExtractorTest extends TestCase {

	public void testExtractsInFieldWithKey() throws SecurityException, NoSuchFieldException {
		FieldAnnotation<In> info = new FieldAnnotation<In>(getInAnnotation("testFieldWithKey", In.class),
				getField("testFieldWithKey"));
		new KeyExtractor().extractInKey(info).equals("in_key_name");
	}

	public void testExtractsInFieldWithoutKey() throws SecurityException, NoSuchFieldException {
		FieldAnnotation<In> info = new FieldAnnotation<In>(getInAnnotation("testFieldWithoutKey", In.class),
				getField("testFieldWithoutKey"));
		new KeyExtractor().extractInKey(info).equals("testFieldWithoutKey");
	}

	@SuppressWarnings("unchecked")
	private <T> T getInAnnotation(String fieldName, Class<T> clazz) throws SecurityException, NoSuchFieldException {
		return (T) getField(fieldName).getAnnotation((Class<? extends Annotation>) clazz);
	}

	private Field getField(String fieldName) throws SecurityException, NoSuchFieldException {
		return FieldTest.class.getDeclaredField(fieldName);
	}

	public static class FieldTest {

		@SuppressWarnings("unused")
		@Out(key = "out_key_name")
		@In(key = "in_key_name")
		@Read(key = "read_key_name")
		@org.vraptor.annotations.Parameter(key = "param_key_name")
		private String testFieldWithKey;

		@SuppressWarnings("unused")
		@Out
		@In
		@Read
		@org.vraptor.annotations.Parameter
		private String testFieldWithoutKey;
	}

	public void testExtractsParameterFieldWithoutKey() throws SecurityException, NoSuchFieldException {
		FieldAnnotation<org.vraptor.annotations.Parameter> info = new FieldAnnotation<org.vraptor.annotations.Parameter>(
				getInAnnotation("testFieldWithoutKey", org.vraptor.annotations.Parameter.class),
				getField("testFieldWithoutKey"));
		new KeyExtractor().extractParamKey(info).equals("testFieldWithoutKey");
	}

	public void testExtractsParameterFieldWithKey() throws SecurityException, NoSuchFieldException {
		FieldAnnotation<org.vraptor.annotations.Parameter> info = new FieldAnnotation<org.vraptor.annotations.Parameter>(
				getInAnnotation("testFieldWithKey", org.vraptor.annotations.Parameter.class),
				getField("testFieldWithKey"));
		new KeyExtractor().extractParamKey(info).equals("param_key_name");
	}

	public void testExtractsReadFieldWithoutKey() throws SecurityException, NoSuchFieldException {
		FieldAnnotation<Read> info = new FieldAnnotation<Read>(getInAnnotation("testFieldWithoutKey", Read.class),
				getField("testFieldWithoutKey"));
		new KeyExtractor().extractReadKey(info).equals("testFieldWithoutKey");
	}

	public void testExtractsReadFieldWithKey() throws SecurityException, NoSuchFieldException {
		FieldAnnotation<Read> info = new FieldAnnotation<Read>(getInAnnotation("testFieldWithKey", Read.class),
				getField("testFieldWithKey"));
		new KeyExtractor().extractReadKey(info).equals("read_key_name");
	}

}
