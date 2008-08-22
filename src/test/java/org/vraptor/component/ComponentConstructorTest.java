package org.vraptor.component;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.introspector.WebBeanProvider;
import org.vraptor.scope.Context;

public class ComponentConstructorTest extends AbstractTest {

	public void testDoesntFindEnoughAttributes() throws InvalidComponentException {
		LogicRequest req = createLogicRequest();
		BeanConstructor type = new ComponentConstructor(Questions.class.getConstructors()[0]);
		try {
			type.newInstance(req, new WebBeanProvider());
			fail();
		} catch (ComponentInstantiationException e) {
			// ok
		}
	}

	public void testConstructsWithAttributeInRequestContext() throws InvalidComponentException,
			ComponentInstantiationException {
		LogicRequest req = createLogicRequest();
		Context logic = req.getRequestContext();
		logic.setAttribute(Type1.class.getName(), new Type1());
		logic.setAttribute(Type2.class.getName(), new Type2());
		logic.setAttribute(Type3.class.getName(), new Type3());
		logic.setAttribute(Type4.class.getName(), new Type4());
		BeanConstructor type = new ComponentConstructor(Questions.class.getConstructors()[0]);
		type.newInstance(req, new WebBeanProvider());
	}

	public static class Questions {
		public Questions(Type1 t1, Type2 t2, Type3 t3, Type4 t4) {
		}
	}

	public static class Type1 {
	}

	public static class Type2 {
	}

	public static class Type3 {
	}

	public static class Type4 {
	}

}
