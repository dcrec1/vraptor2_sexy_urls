package org.vraptor.test;

import org.vraptor.LogicException;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.component.StaticResourceException;
import org.vraptor.core.Controller;
import org.vraptor.core.WebRequest;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.scope.SessionContext;
import org.vraptor.url.InvalidURLException;
import org.vraptor.view.ViewException;

/**
 * The default implementation for the test session.
 * 
 * @author Guilherme Silveira
 */
class DefaultTestSession implements TestSession {

	private final Controller controller;

	private final MockedHttpSession session;

	/**
	 * Intantiates this session test.
	 * 
	 * @param controller
	 */
	DefaultTestSession(Controller controller) {
		this.controller = controller;
		this.session = new MockedHttpSession(new MockedServletContext());
	}

	public String execute(String relativeUrl, String... parameters) throws InvalidComponentException,
			InvalidURLException, ComponentNotFoundException, LogicNotFoundException, ViewException,
			InterceptorInstantiationException, LogicException, StaticResourceException {
		MockedRequest request = new MockedRequest(relativeUrl, session, parameters);
		MockedResponse response = new MockedResponse();
		return controller.execute(new WebRequest(request, response, new MockedServletContext()));
	}

	public SessionContext getSessionContext() {
		return new SessionContext(this.session);
	}

}
