package org.vraptor.scope;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.vraptor.LogicDefinition;
import org.vraptor.LogicRequest;
import org.vraptor.core.WebRequest;
import org.vraptor.url.RequestInfo;

/**
 * Basic vraptor logic request.
 *
 * @author Guilherme Silveira
 */
public class DefaultLogicRequest implements LogicRequest {

	private static final Logger LOG = Logger.getLogger(DefaultLogicRequest.class);

	private final ApplicationContext applicationContext;

	private SessionContext sessionContext;

	private RequestContext requestContext;

	private String result;

	private final RequestInfo info;

	private final WebRequest request;

	private LogicDefinition logicDefinition;

    private final FlashContext flashContext;

	@Deprecated
	public DefaultLogicRequest(RequestInfo info, WebRequest request) {
		this(info, request, null);
	}

	public DefaultLogicRequest(RequestInfo info, WebRequest request, LogicDefinition definition) {
		this.request = request;
		this.info = info;
		this.applicationContext = new DefaultApplicationContext(request.getContext());
		this.logicDefinition = definition;
        this.flashContext = new FlashContext(request.getRequest());
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public SessionContext getSessionContext() {
		if (this.sessionContext == null) {
			this.sessionContext = new SessionContext(this.request.getRequest().getSession());
		}
		return this.sessionContext;
	}

	public RequestContext getRequestContext() {
		if (this.requestContext == null) {
			this.requestContext = new DefaultRequestContext(request.getRequest());
		}
		return this.requestContext;
	}

	public HttpServletRequest getRequest() {
		return request.getRequest();
	}

	public HttpServletResponse getResponse() {
		return request.getResponse();
	}

	public Object findAttribute(String key) {
		if (getRequestContext().hasAttribute(key)) {
			return requestContext.getAttribute(key);
		}
		if (getSessionContext().hasAttribute(key)) {
			return sessionContext.getAttribute(key);
		}
		if (getApplicationContext().hasAttribute(key)) {
			return applicationContext.getAttribute(key);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("key not found in any contexts: " + key);
		}
		return null;
	}

	public void confirmResult(String string) {
		if (this.result != null) {
			throw new IllegalStateException("the result for the logic was already set");
		}
		this.result = string;
	}

	public String getResult() {
		return this.result;
	}

	public RequestInfo getRequestInfo() {
		return info;
	}

	public ServletContext getServletContext() {
		return request.getContext();
	}

	public LogicDefinition getLogicDefinition() {
		return this.logicDefinition;
	}

	public void setLogicDefinition(LogicDefinition logicDefinition) {
		this.logicDefinition = logicDefinition;
	}

    public FlashContext getFlashContext() {
        return this.flashContext;
    }

}
