package org.vraptor.config;

import org.vraptor.webapp.WebApplication;

/**
 * A configuration item.
 * 
 * @author Guilherme Silveira
 */
public interface ConfigItem {

	/**
	 * Registers itself in the web application
	 * 
	 * @param application
	 *            the application
	 * @throws ConfigException
	 *             configuration exception
	 */
	void register(WebApplication application) throws ConfigException;

	/**
	 * Returns true if this is a component, factory or converter
	 * 
	 * @return true or false
	 */
	boolean isComponent();

	/**
	 * Returns true if this is a manager configuration
	 * 
	 * @return true or false
	 */
	boolean isManager();

}
