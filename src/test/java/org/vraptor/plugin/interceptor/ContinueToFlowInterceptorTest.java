package org.vraptor.plugin.interceptor;

import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.core.MockLogicFlow;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.view.ViewException;

public class ContinueToFlowInterceptorTest extends AbstractTest{
	
	public void testRedirectsToTheSpecifiedFlow() throws LogicException, ViewException {
		DefaultLogicRequest request = createLogicRequest();
		MockLogicFlow to = new MockLogicFlow(request);
		MockLogicFlow current = new MockLogicFlow(request);
		ContinueToFlowInterceptor interceptor = new ContinueToFlowInterceptor(to);
		interceptor.intercept(current);
		assertTrue(to.isExecuted());
		assertFalse(current.isExecuted());
	}

}
