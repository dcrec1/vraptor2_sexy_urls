package org.vraptor.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.core.MockLogicFlow;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.view.ViewException;

public class RegisterAttributesInterceptorTest extends AbstractTest {

	private RegisterAttributesInteceptor interceptor;

	private DefaultLogicRequest request;

	private MockLogicFlow flow;

	public static class SimpleRegister {
		public void method() {

		}
	}

	@Override
	protected void setUp() throws Exception {
		this.interceptor = new RegisterAttributesInteceptor();
		this.request = createLogicRequest();
		this.flow = new MockLogicFlow(request);
	}

	@Override
	protected void tearDown() throws Exception {
		this.interceptor = null;
		this.request = null;
		this.flow = null;
	}

	public void testRegistersAllScopeKeys() {
		try {
			interceptor.intercept(flow);
			assertTrue(request.getRequestContext().hasAttribute("request"));
			assertTrue(request.getRequestContext().hasAttribute("response"));
			assertTrue(request.getRequestContext().hasAttribute("application"));
			assertTrue(request.getRequestContext().hasAttribute("session"));
		} catch (LogicException e) {
			fail();
		} catch (ViewException e) {
			fail();
		}
	}
	public void testRegistersAllScopeKeysWithRightAssociations() {
		try {
			interceptor.intercept(flow);
			assertTrue(request.getRequestContext().getAttribute("request").equals(request.getRequest()));
			assertTrue(request.getRequestContext().getAttribute("response").equals(request.getResponse()));
			assertTrue(request.getRequestContext().getAttribute("application").equals(request.getServletContext()));
			assertTrue(request.getRequestContext().getAttribute("session").equals(request.getRequest().getSession()));

			assertTrue(request.getRequestContext().getAttribute("httpServletRequest").equals(request.getRequest()));
			assertTrue(request.getRequestContext().getAttribute("httpServletResponse").equals(request.getResponse()));
			assertTrue(request.getRequestContext().getAttribute("servletContext").equals(request.getServletContext()));
			assertTrue(request.getRequestContext().getAttribute("httpSession").equals(request.getRequest().getSession()));
		} catch (LogicException e) {
			fail();
		} catch (ViewException e) {
			fail();
		}
	}

	public void testRegistersAllKeysForConstructorInjection() {
		try {
			interceptor.intercept(flow);
			assertTrue(request.getRequestContext().hasAttribute(HttpSession.class.getName()));
			assertTrue(request.getRequestContext().hasAttribute(ServletContext.class.getName()));
			assertTrue(request.getRequestContext().hasAttribute(HttpServletRequest.class.getName()));
			assertTrue(request.getRequestContext().hasAttribute(HttpServletResponse.class.getName()));
		} catch (LogicException e) {
			fail();
		} catch (ViewException e) {
			fail();
		}
	}

}
