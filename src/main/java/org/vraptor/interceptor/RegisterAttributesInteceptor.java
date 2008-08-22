package org.vraptor.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.scope.RequestContext;
import org.vraptor.view.ViewException;

public class RegisterAttributesInteceptor implements Interceptor {

	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		DefaultLogicRequest req = (DefaultLogicRequest) flow.getLogicRequest();

		RequestContext request = flow.getLogicRequest().getRequestContext();

		request.setAttribute("request", req.getRequest());
		request.setAttribute("httpServletRequest", req.getRequest());
		request.setAttribute(HttpServletRequest.class.getName(), req.getRequest());

		request.setAttribute("response", req.getResponse());
		request.setAttribute("httpServletResponse", req.getResponse());
		request.setAttribute(HttpServletResponse.class.getName(), req.getResponse());

		request.setAttribute("application", req.getServletContext());
		request.setAttribute("servletContext", req.getServletContext());
		request.setAttribute(ServletContext.class.getName(), req.getRequest().getSession().getServletContext());

		request.setAttribute("session", req.getRequest().getSession());
		request.setAttribute("httpSession", req.getRequest().getSession());
		request.setAttribute(HttpSession.class.getName(), req.getRequest().getSession());

		flow.execute();
	}
}
