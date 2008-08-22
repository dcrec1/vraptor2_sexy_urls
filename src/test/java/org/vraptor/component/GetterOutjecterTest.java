package org.vraptor.component;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.vraptor.annotations.Out;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.scope.ScopeType;

public class GetterOutjecterTest extends TestCase {

	public static class GetterCustomOut {

		@Out(scope = ScopeType.SESSION)
		public String getVal() {
			return "oi2";
		}

	}

	private Method grabGetter(Class type) throws SecurityException, NoSuchMethodException {
		return type.getMethod("getVal");
	}

	public void testReadsOverridenScope() throws SecurityException, NoSuchMethodException {
		GetterOutjecter outjecter = new GetterOutjecter("val", grabGetter(GetterCustomOut.class));
		assertEquals(ScopeType.SESSION, outjecter.getScope());
	}

	public void testRetrievesTheObject() throws SecurityException, NoSuchMethodException, MethodInvocationException {
		GetterOutjecter outjecter = new GetterOutjecter("val", grabGetter(GetterCustomOut.class));
		GetterCustomOut obj = new GetterCustomOut();
		assertEquals("oi2", outjecter.getValue(obj));
	}

}
