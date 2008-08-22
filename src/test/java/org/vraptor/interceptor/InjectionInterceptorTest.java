package org.vraptor.interceptor;

import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.annotations.In;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.core.DefaultLogicDefinition;
import org.vraptor.core.MockLogicFlow;
import org.vraptor.introspector.BasicIntrospector;
import org.vraptor.reflection.SettingException;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.view.ViewException;

public class InjectionInterceptorTest extends AbstractTest {

	public static class TestOk {

		@In
		private String a;

	}

	public void testInjects() throws InvalidComponentException, LogicException, ViewException {
		DefaultLogicRequest request = createLogicRequest();
		TestOk component = new TestOk();

		request.getRequestContext().setAttribute("a", "b");
		ComponentType type = createComponentType(TestOk.class);

		request.setLogicDefinition(new DefaultLogicDefinition(component, type, null));

		MockLogicFlow flow = new MockLogicFlow(request);
		InjectionInterceptor interceptor = new InjectionInterceptor(new BasicIntrospector());
		interceptor.intercept(flow);
		assertEquals("b", component.a);
	}

	public void testDoesNotFindTheObjectInTheScope() throws InvalidComponentException, ViewException {
		TestOk component = new TestOk();

		ComponentType type = createComponentType(TestOk.class);
		DefaultLogicRequest request = createLogicRequest("", "");

		MockLogicFlow flow = new MockLogicFlow(request);

		request.setLogicDefinition(new DefaultLogicDefinition(component, type, null));

		InjectionInterceptor interceptor = new InjectionInterceptor(new BasicIntrospector());
		try {
			interceptor.intercept(flow);
			fail();
		} catch (LogicException e) {
			assertEquals(SettingException.class, e.getCause().getClass());
		}
	}

	public void testIsUnableToInstantiateObject() throws InvalidComponentException, ViewException {

		TestUnable component = new TestUnable("");

		ComponentType type = createComponentType(TestUnable.class);
		DefaultLogicRequest request = createLogicRequest("", "");
		MockLogicFlow flow = new MockLogicFlow(request);

		request.setLogicDefinition(new DefaultLogicDefinition(component, type, null));

		InjectionInterceptor interceptor = new InjectionInterceptor(new BasicIntrospector());
		try {
			interceptor.intercept(flow);
			fail();
		} catch (LogicException e) {
			assertEquals(ComponentInstantiationException.class, e.getCause().getClass());
		}
	}

	public static class TestUnable {

		@SuppressWarnings("unused")
		@In(create = true)
		private TestUnable unable;

		public TestUnable(String s) {
		}

	}

}
