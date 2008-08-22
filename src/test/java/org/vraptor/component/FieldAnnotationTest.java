package org.vraptor.component;

import java.lang.reflect.Field;
import junit.framework.TestCase;
import org.vraptor.annotations.In;

public class FieldAnnotationTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testFieldAnnotation() throws SecurityException,
			NoSuchFieldException {
		Field field = Test.class.getDeclaredField("field");
		In annotation = field.getAnnotation(In.class);
		FieldAnnotation test = new FieldAnnotation<In>(annotation, field);
		assertEquals(annotation,test.getAnnotation());
		assertEquals(field,test.getField());
	}

	public class Test {
		@SuppressWarnings("unused")
		@In
		private int field;
	}

}
