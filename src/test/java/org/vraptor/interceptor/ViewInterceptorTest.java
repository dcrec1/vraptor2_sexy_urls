package org.vraptor.interceptor;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.LogicDefinition;
import org.vraptor.LogicException;
import org.vraptor.LogicRequest;
import org.vraptor.core.MockLogicFlow;
import org.vraptor.view.ViewException;
import org.vraptor.view.ViewManager;

public class ViewInterceptorTest extends AbstractTest {

	public void testShouldNotRedirect() throws LogicException, ViewException {
		MockLogicFlow flow = new MockLogicFlow((LogicRequest) getRedirectionRequest(false).proxy());
		Mock mock = mock(ViewManager.class);

		ViewInterceptor interceptor = new ViewInterceptor((ViewManager) mock.proxy());
		interceptor.intercept(flow);
	}

	private Mock getRedirectionRequest(boolean b) {
		Mock logicDefinition = mock(LogicDefinition.class);
		Mock mock = mock(LogicRequest.class);
		mock.expects(once()).method("getResult").withNoArguments().will(returnValue("ok"));
		mock.expects(once()).method("getLogicDefinition").will(returnValue(logicDefinition.proxy()));
		logicDefinition.expects(once()).method("getLogicMethod").withNoArguments().will(returnValue(new MockedLogicMethod(b)));
		return mock;
	}

	public void testShouldNotFlowAfterShouldNotRedirect() throws LogicException, ViewException {
		MockLogicFlow flow = new MockLogicFlow((LogicRequest) getRedirectionRequest(false).proxy());

		MockedViewManager manager = new MockedViewManager();
		ViewInterceptor interceptor = new ViewInterceptor(manager);
		interceptor.intercept(flow);
		assertFalse(flow.isRedirected());
	}

	public void testShouldRedirect() throws LogicException, ViewException {
		MockLogicFlow flow = new MockLogicFlow((LogicRequest) getRedirectionRequest(true).proxy());

		Mock mock = mock(ViewManager.class);
		mock.expects(once()).method("forward");

		ViewInterceptor interceptor = new ViewInterceptor((ViewManager) mock.proxy());
		interceptor.intercept(flow);

	}

	public void testShouldNotFlowAfterShouldRedirect() throws LogicException, ViewException {
		MockLogicFlow flow = new MockLogicFlow((LogicRequest) getRedirectionRequest(true).proxy());

		Mock mock = mock(ViewManager.class);
		mock.expects(once()).method("forward");

		ViewInterceptor interceptor = new ViewInterceptor((ViewManager) mock.proxy());
		interceptor.intercept(flow);
		assertFalse(flow.isRedirected());

	}

}
