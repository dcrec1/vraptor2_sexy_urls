package org.vraptor.test;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.component.StaticResourceException;
import org.vraptor.core.Controller;
import org.vraptor.core.WebRequest;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.url.InvalidURLException;
import org.vraptor.view.ViewException;

public class DefaultTestSessionTest extends AbstractTest {

	public void testInvokesExecution() throws InterceptorInstantiationException, InvalidComponentException, InvalidURLException, ComponentNotFoundException, LogicNotFoundException, ViewException, LogicException, StaticResourceException {

		Mock mock = mock(Controller.class);
		mock.expects(once()).method("execute").with(isA(WebRequest.class));

		DefaultTestSession session = new DefaultTestSession((Controller) mock.proxy());
		session.execute("");

	}

}
