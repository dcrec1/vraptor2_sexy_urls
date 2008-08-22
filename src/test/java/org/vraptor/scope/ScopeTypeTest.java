package org.vraptor.scope;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;

public class ScopeTypeTest extends AbstractTest {

	public void testRequest() {
		LogicRequest request = createLogicRequest();
		assertEquals(request.getRequestContext(), (ScopeType.REQUEST.getContext(request)));
	}

	public void testSession() {
		LogicRequest request = createLogicRequest();
		assertEquals(request.getSessionContext(), (ScopeType.SESSION.getContext(request)));
	}

	public void testApplication() {
		LogicRequest request = createLogicRequest();
		assertEquals(request.getApplicationContext(), (ScopeType.APPLICATION.getContext(request)));
	}

}
