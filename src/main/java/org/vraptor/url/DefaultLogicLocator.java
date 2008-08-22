package org.vraptor.url;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.component.StaticResourceException;

/**
 * The default url manager translates url data.
 * 
 * @author Guilherme Silveira
 */
public class DefaultLogicLocator implements LogicLocator {

	private static final Logger LOG = Logger
			.getLogger(DefaultLogicLocator.class);

	private static final String INCLUDE_REQUEST_URI = "javax.servlet.include.request_uri";

	private final ComponentManager manager;

	public DefaultLogicLocator(ComponentManager manager) {
		this.manager = manager;
	}

	public LogicMethod locate(HttpServletRequest req)
			throws InvalidURLException, LogicNotFoundException,
			ComponentNotFoundException, StaticResourceException {
		String uri = extractLogicInfoFromRequestURI(req);

		if (existsADotIn(uri) || isWelcomeFileRequest(uri)) {
			throw new StaticResourceException();
		}

		int lastPositionOfSlash = uri.lastIndexOf("/");
		if (doesntExists(lastPositionOfSlash)) {
			throw new InvalidURLException("Unable to deal with desired url: "
					+ uri);
		}

		String componentName = uri.substring(0, lastPositionOfSlash);
		String actionName = parseActionNameFrom(uri
				.substring(lastPositionOfSlash + 1));
		ComponentType component = manager.getComponent(componentName,
				actionName);
		LogicMethod method = component.getLogic(actionName);

		if (LOG.isDebugEnabled()) {
			LOG.debug("logic method extracted: " + method);
		}
		return method;
	}

	private boolean isWelcomeFileRequest(String uri) {
		return uri.equals("");
	}

	String extractLogicInfoFromRequestURI(HttpServletRequest req) {
		String uri = extractURI(req);
		if (!uri.equals("")) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Requested url (request wrapped): " + uri);
			}

			String contextPath = req.getContextPath();
			int startPosition = uri.indexOf(contextPath) + contextPath.length()
					+ 1;

			uri = uri.substring(startPosition);
			if (LOG.isDebugEnabled()) {
				LOG.debug("requested uri: " + uri);
			}
		}
		return uri;
	}

	private String extractURI(HttpServletRequest req) {
		String uri;
		// details in :
		// http://www.caucho.com/resin-3.0/webapp/faq.xtp#forward-path
		// and
		// http://www.javaworld.com/javaworld/jw-03-2003/jw-0328-servlet.html?page=4
		if (req.getAttribute(INCLUDE_REQUEST_URI) != null) {
			uri = (String) req.getAttribute(INCLUDE_REQUEST_URI);
		} else {
			uri = req.getRequestURI();
		}

		return uri;
	}

	private String parseActionNameFrom(String str) {
		int position = str.indexOf("?");
		if (position != -1) {
			return str.substring(0, position);
		} else {
			if (str.equals("")) {
				return "index";
			} else {
				return str;
			}
		}
	}

	private boolean doesntExists(int lastIndex) {
		return lastIndex == -1;
	}

	private boolean existsADotIn(String uri) {
		return uri.contains(".");
	}

}
