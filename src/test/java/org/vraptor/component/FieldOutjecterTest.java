package org.vraptor.component;

import junit.framework.TestCase;

import org.vraptor.annotations.Out;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.scope.ScopeType;

public class FieldOutjecterTest extends TestCase {

	public static class FieldOut {

		@SuppressWarnings("unused")
		@Out
		private String val;

	}

	public static class CustomFieldOut {

		@SuppressWarnings("unused")
		@Out(key = "a", scope = ScopeType.SESSION)
		private String val;

	}

	public void testReadsOverridenKey() {
		FieldOutjecter outjecter = new FieldOutjecter(grabField(CustomFieldOut.class));
		assertEquals("a", outjecter.getKey());
	}

	private FieldAnnotation<Out> grabField(Class type) {
		return ReflectionUtil.readAnnotations(type, Out.class).get(0);
	}

	public void testReadsDefaultKey() {
		FieldOutjecter outjecter = new FieldOutjecter(grabField(FieldOut.class));
		assertEquals("val", outjecter.getKey());
	}

	public void testReadsOverridenScope() {
		FieldOutjecter outjecter = new FieldOutjecter(grabField(CustomFieldOut.class));
		assertEquals(ScopeType.SESSION, outjecter.getScope());
	}

	public void testRetrievesTheObject() throws GettingException, MethodInvocationException {
		FieldOutjecter outjecter = new FieldOutjecter(grabField(FieldOut.class));
		FieldOut obj = new FieldOut();
		obj.val = "oi";
		assertEquals("oi", outjecter.getValue(obj));
	}
}
