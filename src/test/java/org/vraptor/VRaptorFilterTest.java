package org.vraptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.jmock.Mock;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.core.Controller;
import org.vraptor.test.MockedServletContext;

public class VRaptorFilterTest extends AbstractTest{

	private Mock controllerMock;
	private Mock chain;
	private VRaptorFilter filter;

	public void testInvokesItsControllerExecution() throws ServletException, IOException {

		controllerMock.expects(once()).method("execute");
		
		filter.doFilter(null, null, (FilterChain) chain.proxy());

	}

	public void testGivesA404IfLogicNotFound() throws ServletException, IOException {

		controllerMock.expects(once()).method("execute").will(throwException(new LogicNotFoundException("")));
		Mock response = mock(HttpServletResponse.class);
		response.expects(once()).method("sendError").with(eq(404), isA(String.class));
		filter.doFilter(null, (HttpServletResponse) response.proxy(), (FilterChain) chain.proxy());

	}

	public void testGivesA404IfComponentNotFound() throws ServletException, IOException {

		controllerMock.expects(once()).method("execute").will(throwException(new ComponentNotFoundException("")));
		Mock response = mock(HttpServletResponse.class);
		response.expects(once()).method("sendError").with(eq(404), isA(String.class));
		
		filter.doFilter(null, (HttpServletResponse) response.proxy(), (FilterChain) chain.proxy());

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.controllerMock = mock(Controller.class);
		this.filter = new VRaptorFilter((Controller) controllerMock.proxy());
		Mock config = mock(FilterConfig.class);
		config.expects(once()).method("getServletContext").will(returnValue(new MockedServletContext()));
		chain = mock(FilterChain.class);
		filter.init((FilterConfig) config.proxy());
	}

}
