package org.vraptor.scope;

import javax.servlet.http.HttpSession;


/**
 * Simple decorator for the session, builds up some basic functionality.
 * 
 * @author Guilherme Silveira
 */
public class SessionContext implements Context {

	private HttpSession session;

	/**
	 * Constructor for this decorator
	 * 
	 * @param session
	 *            session
	 */
	public SessionContext(HttpSession session) {
		this.session = session;
	}

	/**
	 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String arg0) {
		return this.session.getAttribute(arg0);
	}

	/**
	 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String arg0, Object arg1) {
		this.session.setAttribute(arg0, arg1);
	}

	/**
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	public void setMaxInactiveInterval(int arg0) {
		this.session.setMaxInactiveInterval(arg0);
	}

	/**
	 * Whether it has an attribute
	 * 
	 * @see org.vraptor.scope.Context#hasAttribute(java.lang.String)
	 */
	public boolean hasAttribute(String key) {
		return this.session.getAttribute(key) != null;
	}

	/**
	 * Removes an attribute
	 * 
	 * @see org.vraptor.scope.Context#removeAttribute(java.lang.String)
	 */
	public Object removeAttribute(String name) {
		Object obj = getAttribute(name);
		this.session.removeAttribute(name);
		return obj;
	}

}
