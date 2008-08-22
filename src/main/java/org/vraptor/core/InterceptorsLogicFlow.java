package org.vraptor.core;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.RedirectException;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.interceptor.InterceptorDealer;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.SettingException;
import org.vraptor.view.ViewException;

/**
 * The base implementation of logic flow, calling the given interceptors.
 * 
 * @author Guilherme Silveira
 * @author Paulo Silveira
 */
public class InterceptorsLogicFlow implements LogicFlow {

	private static final Logger LOG = Logger.getLogger(InterceptorsLogicFlow.class);

	private final InterceptorDealer interceptorDealer;

	private Interceptor currentInterceptor;

	private final LogicRequest request;

	public InterceptorsLogicFlow(LogicRequest request, InterceptorDealer dealer) {
		this.request = request;
		this.interceptorDealer = dealer;
	}

	/**
	 * Executes the next step in the flow, keeping in mind the last interceptor
	 * executed, so we can execute the next one.
	 * 
	 */
	public void execute() throws ViewException, LogicException {
		if (currentInterceptor != null) {
			try {
				interceptorDealer.outject(currentInterceptor, request);
			} catch (GettingException e) {
				throw new LogicException(e.getMessage(), e);
			} catch (MethodInvocationException e) {
				throw new LogicException(e);
			}
		}

		currentInterceptor = interceptorDealer.poll();

		try {
			interceptorDealer.inject(currentInterceptor, request);
		} catch (ComponentInstantiationException e) {
			throw new LogicException(e.getMessage(), e);
		} catch (SettingException e) {
			throw new LogicException(e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			//TODO trace
			LOG.debug("Calling interceptor: " + currentInterceptor);
		}
		currentInterceptor.intercept(this);
	}

	/**
	 * Adds an interceptor to the top.
	 * @param i	the interceptor to add
	 */
	public void addInterceptor(InterceptorType i) {
		interceptorDealer.add(i);
	}

	public void redirect(String url) throws RedirectException {
		LOG.warn("redirecting using a deprecated method. See LogicFlow.redirect");
		try {
			request.getRequest().getRequestDispatcher(url).forward(request.getRequest(), request.getResponse());
		} catch (ServletException e) {
			if (e.getRootCause() == null) {
				throw new RedirectException(e.getMessage(), e);
			}
			throw new RedirectException(e.getRootCause().getMessage(), e.getRootCause());
		} catch (IOException e) {
			throw new RedirectException(e.getMessage(), e);
		}
	}

	public LogicRequest getLogicRequest() {
		return this.request;
	}

}
