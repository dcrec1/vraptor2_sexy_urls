package org.vraptor.core;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple wrapper for request, response and context
 * 
 * @author Paulo Silveira
 * @since 2.2.3
 */
public class WebRequest {

	private HttpServletRequest request;

	private HttpServletResponse response;

	private ServletContext context;

	public WebRequest(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
		super();
		this.request = request;
		this.response = response;
		this.context = context;
	}

	public ServletContext getContext() {
		return context;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

}
