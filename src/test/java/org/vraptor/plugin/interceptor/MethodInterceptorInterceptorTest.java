package org.vraptor.plugin.interceptor;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicMethod;
import org.vraptor.core.DefaultLogicDefinition;
import org.vraptor.introspector.BasicIntrospector;
import org.vraptor.test.MockedRequest;
import org.vraptor.view.ViewException;

public class MethodInterceptorInterceptorTest extends AbstractTest {

	public void testMethodAnnotatedWithInterceptedBy() throws LogicException, ViewException, SecurityException, NoSuchMethodException, InvalidComponentException {
		MethodInterceptorInterceptor interceptor = new MethodInterceptorInterceptor(new BasicIntrospector());

		MockedRequest request = createRequest();

		Mock logicRequestMock = mock(LogicRequest.class);
		LogicMethod logicMethod = createLogicMethod(MethodTest.class.getMethod("methodWithAnnotation"));
		logicRequestMock.expects(once()).method("getLogicDefinition").will(returnValue(new DefaultLogicDefinition(null, null, logicMethod)));
		LogicRequest logicRequest = (LogicRequest) logicRequestMock.proxy();

		logicRequestMock.expects(once()).method("getRequest").will(returnValue(request));

		Mock flowMock = mock(LogicFlow.class);
		flowMock.expects(atLeastOnce()).method("getLogicRequest").will(returnValue(logicRequest));
		LogicFlow flow = (LogicFlow) flowMock.proxy();
		interceptor.intercept(flow);

		assertEquals("ok", request.getAttribute("ran"));
	}

	public void testIgnoresMethodsNotAnnotatedWithInterceptedBy() throws SecurityException, NoSuchMethodException, LogicException, ViewException, InvalidComponentException {
		MethodInterceptorInterceptor interceptor = new MethodInterceptorInterceptor(new BasicIntrospector());

		Mock logicRequestMock = mock(LogicRequest.class);
		LogicMethod logicMethod = createLogicMethod(MethodTest.class.getMethod("methodWithoutAnnotation"));
		logicRequestMock.expects(once()).method("getLogicDefinition").will(returnValue(new DefaultLogicDefinition(null, null, logicMethod)));
		LogicRequest logicRequest = (LogicRequest) logicRequestMock.proxy();

		Mock flowMock = mock(LogicFlow.class);
		flowMock.expects(atLeastOnce()).method("getLogicRequest").will(returnValue(logicRequest));
		flowMock.expects(once()).method("execute");

		LogicFlow flow = (LogicFlow) flowMock.proxy();
		interceptor.intercept(flow);
	}

	public static class MethodTest {
		@InterceptedBy(NewInterceptor.class)
		public void methodWithAnnotation() {

		}

		public void methodWithoutAnnotation() {

		}
	}

	public static class NewInterceptor implements Interceptor {
		static NewInterceptor lastInstance;

		public NewInterceptor() {
			lastInstance = this;
		}

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			flow.getLogicRequest().getRequest().setAttribute("ran", "ok");
		}

	}

}
