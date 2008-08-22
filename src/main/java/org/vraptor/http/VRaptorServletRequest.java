package org.vraptor.http;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.vraptor.LogicRequest;
import org.vraptor.introspector.Introspector;

/**
 * A simple request wrapper for dealing with variable lookup. It is used due to
 * the multipartrequestinterceptor.
 *
 * @author Guilherme Silveira
 */
public class VRaptorServletRequest extends HttpServletRequestWrapper {

	private LogicRequest logicRequest;

	private final RequestParameters parameters;

	private final Introspector introspector;

	/**
	 * Constructor for this wrapper
	 *
	 * @param req
	 *            request
	 * @param manager
	 *            the controller
	 */
	@SuppressWarnings("unchecked")
	public VRaptorServletRequest(HttpServletRequest req, Introspector introspector) {
		super(req);
		this.introspector = introspector;
		this.parameters = new RequestParameters(req.getParameterMap());
	}

	@Override
	public Object getAttribute(String key) {
		return super.getAttribute(key);
	}

	/**
	 * Changes the current logicRequest
	 *
	 * @param logicRequest
	 *            logicRequest
	 */
	public void setCurrentRequest(LogicRequest context) {
		this.logicRequest = context;
	}

	public void overwriteParameters(String name, String value) {
		this.parameters.put(name, value);
	}

	@Override
	public String getParameter(String name) {
		return parameters.getParameter(name);
	}

	@Override
	public Map getParameterMap() {
		return parameters.getParameterMap();
	}

	@Override
	public Enumeration getParameterNames() {
		return parameters.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String name) {
		return parameters.getParameterValues(name);
	}

}
