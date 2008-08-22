package org.vraptor.scope;

import java.util.Set;

import javax.servlet.ServletContext;

/**
 * The basic implementation of the application context.
 * 
 * @author Guilherme Silveira
 */
public class DefaultApplicationContext implements ApplicationContext {

	private ServletContext servletContex;

	public DefaultApplicationContext(ServletContext application) {
		this.servletContex = application;
	}

	public boolean hasAttribute(String name) {
		return servletContex.getAttribute(name) != null;
	}

	public void setAttribute(String name, Object value) {
		servletContex.setAttribute(name, value);
	}

	public Object getAttribute(String name) {
		return servletContex.getAttribute(name);
	}

	public Object removeAttribute(String name) {
		Object value = servletContex.getAttribute(name);
		servletContex.removeAttribute(name);
		return value;
	}

	public String getRealPath(String path) {
		return servletContex.getRealPath(path);
	}

	public Set getResourcePaths(String directory) {
		return servletContex.getResourcePaths(directory);
	}

}
