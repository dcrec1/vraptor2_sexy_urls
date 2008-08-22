package org.vraptor.interceptor;

import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.annotations.Out;
import org.vraptor.component.ComponentType;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.core.DefaultLogicDefinition;
import org.vraptor.core.MockLogicFlow;
import org.vraptor.introspector.BasicIntrospector;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.view.ViewException;

public class OutjectionInterceptorTest extends AbstractTest {

	public static class TestOk {

		@SuppressWarnings("unused")
		@Out
		private String attribute = "value";

	}

	public void testOutjectsSimpleString() throws InvalidComponentException, LogicException, ViewException {

		DefaultLogicRequest request = createLogicRequest();
		ComponentType type = createComponentType(TestOk.class);
		request.setLogicDefinition(new DefaultLogicDefinition(new TestOk(), type, null));

		OutjectionInterceptor interceptor = new OutjectionInterceptor(new BasicIntrospector());
		interceptor.intercept(new MockLogicFlow(request));
		assertEquals("value", request.getRequestContext().getAttribute("attribute"));

	}

}
