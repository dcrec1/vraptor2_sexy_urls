package org.vraptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.component.StaticResourceException;
import org.vraptor.config.ConfigException;
import org.vraptor.core.Controller;
import org.vraptor.core.ControllerFactory;
import org.vraptor.core.WebRequest;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.url.InvalidURLException;
import org.vraptor.view.ViewException;

/**
 * VRaptor servlet contains a vraptor controller capable of answering web
 * requests.
 *
 * @author Guilherme Silveira
 */
public class VRaptorFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(VRaptorFilter.class);

	/** serialversion uid */
	private static final long serialVersionUID = 1084551538406918486L;

	private final static String CHECK_THE_LOGS_MESAGE = " For further information, check the log files for the root cause."
			+ " Enable VRaptor DEBUG/TRACE messages in log4J if necessary.";

	private Controller controller;

	private boolean initialized;
	
	private FilterConfig filterConfig = null;

	public VRaptorFilter(Controller controller) {
		this.controller = controller;
		this.initialized = true;
	}

	public VRaptorFilter() {
	}

	private void sendError(HttpServletResponse response, int error,
			String message, Exception e) throws IOException {
		StringBuilder finalMessage = new StringBuilder(message);
		finalMessage.append(" Cause: ");
		finalMessage.append(e.toString());
		finalMessage.append(". ");
		finalMessage.append(CHECK_THE_LOGS_MESAGE);
		response.sendError(error, finalMessage.toString());

	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (LOG.isDebugEnabled()) {
			LOG.debug("new request to " + httpRequest.getRequestURI());
		}
		try {
			controller.execute(new WebRequest(httpRequest, httpResponse, getServletContext()));
		} catch (LogicNotFoundException e) {
			LOG.debug("Logic not found", e);
			sendError(httpResponse, 404, "URL not found, no logic matches for this component.", e);
		} catch (InvalidURLException ex) {
			throw new ServletException("Unable to deal with selected url", ex);
		} catch (ComponentNotFoundException e) {
			LOG.debug("Component not found", e);
			sendError(httpResponse, 404, "URL not found, no registered component matches.", e);
		} catch (InterceptorInstantiationException e) {
			LOG.debug("Instatiating an interceptor resulted an exception.", e);
			throw new ServletException(e.getMessage(), e);
		} catch (ViewException ex) {
			LOG.debug("Rendering view resulted an exception.", ex);
			if (ex.getCause() == null) {
				throw new ServletException(ex.getMessage(), ex);
			} else {
				throw new ServletException(ex.getCause().getMessage(), ex.getCause());
			}
		} catch (LogicException ex) {
			LOG.debug("Logic exception.", ex);

			if (ex.getCause() == null) {
				throw new ServletException(ex.getMessage(), ex);
			} else {
				throw new ServletException(ex.getCause().getMessage(), ex.getCause());
			}
		} catch (StaticResourceException ex) {
			chain.doFilter(request, response);
		} catch (Exception e) {
			LOG.debug("VRaptor unexpected exception", e);
			throw new ServletException(e);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		if (!this.initialized) {
			try {
				this.controller = new ControllerFactory().configure(getServletContext());
			} catch (ConfigException e) {
				throw new ServletException("Unable to configure vraptor2", e);
			}
			this.initialized = true;
		}
	}

	private ServletContext getServletContext() {
		return filterConfig.getServletContext();
	}
	
	

}
