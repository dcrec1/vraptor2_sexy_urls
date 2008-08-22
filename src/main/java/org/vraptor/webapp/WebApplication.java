package org.vraptor.webapp;

import org.vraptor.component.ComponentContainer;
import org.vraptor.component.ComponentManager;
import org.vraptor.config.ConfigException;
import org.vraptor.converter.ConverterManager;
import org.vraptor.introspector.Introspector;
import org.vraptor.scope.ApplicationContext;
import org.vraptor.url.LogicLocator;
import org.vraptor.url.ViewLocator;
import org.vraptor.view.ViewManager;

/**
 * A web application.
 * 
 * @author Guilherme Silveira
 */
public interface WebApplication {

	/**
	 * Returns the url manager
	 * 
	 * @return the url manager
	 */
	LogicLocator getLogicLocator();

	/**
	 * Initializes the web application
	 * 
	 * @throws ConfigException
	 * 
	 */
	void init() throws ConfigException;

	/**
	 * Returns the component manager
	 * 
	 * @return the component manager
	 */
	ComponentManager getComponentManager();

	/**
	 * Returns the view manager
	 * 
	 * @return the view manager
	 */
	ViewManager getDefaultViewManager();

	/**
	 * Returns the converter manager
	 * 
	 * @return the converter manager
	 */
	ConverterManager getConverterManager();

	/**
	 * Returns the plugin manager
	 * 
	 * @return the plugin manager
	 */
	PluginManager getPluginManager();

	/**
	 * Returns the application context.
	 * 
	 * @return the application context
	 */
	ApplicationContext getApplicationContext();

	void setViewManager(ViewManager viewManager);

	/**
	 * Returns the application introspector.
	 * 
	 * @return the application introspector
	 * @since 2.2.4
	 */
	Introspector getIntrospector();

	ComponentContainer getContainer();

}
