package org.vraptor.interceptor;

import java.util.ArrayList;

import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.annotations.Remotable;
import org.vraptor.component.ComponentType;
import org.vraptor.component.DefaultLogicMethod;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.MethodParameter;
import org.vraptor.core.DefaultLogicDefinition;
import org.vraptor.core.MockLogicFlow;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.view.ViewException;

public class ExecuteLogicInterceptorTest extends AbstractTest {

	public static class TestClass {
		private boolean executed;

		public boolean isExecuted() {
			return executed;
		}

		public void methodNotRemotable() {
		}

		@Remotable
		public void methodRemotable() {
		}

		public String simpleMethod() {
			executed = true;
			return "result";
		}
	}

	public void testSimpleMethod() throws SecurityException, NoSuchMethodException, LogicException, ViewException,
			InvalidComponentException {

		DefaultLogicMethod m = new DefaultLogicMethod(registry.getValidationErrorsFactory(),"simpleMethod", TestClass.class.getDeclaredMethod("simpleMethod"), null, new ArrayList<MethodParameter>());
		ExecuteLogicInterceptor i = new ExecuteLogicInterceptor();

		DefaultLogicRequest request = createLogicRequest("x", "simpleMethod");
		DefaultLogicDefinition logicDefinition = new DefaultLogicDefinition(null,null,null);
		logicDefinition.setParameters(new Object[0]);
		request.setLogicDefinition(logicDefinition);

		TestClass component = new TestClass();
		ComponentType type = createComponentType(TestClass.class);
		request.setLogicDefinition(new DefaultLogicDefinition(component, type, m));
		MockLogicFlow flow = new MockLogicFlow(request);

		i.intercept(flow);

		assertTrue(component.isExecuted());
		assertEquals("result", flow.getLogicRequest().getResult());
	}
}
