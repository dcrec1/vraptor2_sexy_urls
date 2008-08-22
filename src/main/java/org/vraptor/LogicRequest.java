package org.vraptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vraptor.scope.Context;
import org.vraptor.scope.FlashContext;
import org.vraptor.scope.RequestContext;
import org.vraptor.scope.SessionContext;
import org.vraptor.url.RequestInfo;

/**
 * An interface to extract from the url which action and component should be
 * called and contains a logic request information.
 *
 * @author Guilherme Silveira
 */
public interface LogicRequest {

	HttpServletRequest getRequest();

	HttpServletResponse getResponse();

	ServletContext getServletContext();

	RequestInfo getRequestInfo();

	/**
	 * Returns a wrapper for the application context
	 *
	 * @return the context
	 */
	Context getApplicationContext();

	/**
	 * Returns a wrapper for the user session
	 *
	 * @return the context
	 */
	SessionContext getSessionContext();

	/**
	 * Returns a wrapper for the request session
	 *
	 * @return the context
	 */
	RequestContext getRequestContext();

	/**
	 * Looks for an attribute in any scope.
	 *
	 * @param key
	 *            the key to look for
	 * @return the object or null if not found
	 */
	Object findAttribute(String key);

	/**
	 * Sets the String that will represent the logic result, like success,
	 * invalid, error and so on
	 *
	 * @param string
	 * @throws IllegalStateException
	 *             if this value was already set
	 *
	 */
	void confirmResult(String string);

	/**
	 * Returns the current value set as the logic result
	 *
	 * @return the result for now, null if it was not set yet
	 */
	String getResult();

	LogicDefinition getLogicDefinition();

	void setLogicDefinition(LogicDefinition definition);

    FlashContext getFlashContext();

}