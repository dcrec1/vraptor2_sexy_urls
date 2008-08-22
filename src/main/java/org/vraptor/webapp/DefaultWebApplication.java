package org.vraptor.webapp;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.vraptor.component.ComponentContainer;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.DefaultComponentContainer;
import org.vraptor.config.ConfigException;
import org.vraptor.config.Configuration;
import org.vraptor.converter.ConverterManager;
import org.vraptor.converter.SimpleConverterManager;
import org.vraptor.introspector.BasicIntrospector;
import org.vraptor.introspector.Introspector;
import org.vraptor.plugin.DefaultPluginManager;
import org.vraptor.scope.ApplicationContext;
import org.vraptor.url.LogicLocator;
import org.vraptor.view.RegexViewManager;
import org.vraptor.view.ViewManager;

/**
 * A simple web application configuration. It uses the default url manager.
 * 
 * @author Guilherme Silveira
 * @author Paulo Silveira
 */
public class DefaultWebApplication implements WebApplication {

	private static final String JSP_REGEX = "/$1/$2.$3.jsp";

	private static final Logger LOG = Logger.getLogger(DefaultWebApplication.class);

	private final Introspector introspector;

	private final ConverterManager converterManager;

	private final ComponentManager componentManager;

	private ViewManager viewManager;

	private final PluginManager pluginManager;

	private final ApplicationContext applicationContext;

	private final LogicLocator logicLocator;

	private final ComponentContainer container;

	public DefaultWebApplication(ServletContext context) {
		this.container = new DefaultComponentContainer(context);
		applicationContext = container.getApplicationContext();
		componentManager = container.getComponentManager();
		logicLocator = container.getLogicLocator();
		viewManager = new RegexViewManager(JSP_REGEX);
		converterManager = new SimpleConverterManager();
		pluginManager = new DefaultPluginManager();
		introspector = new BasicIntrospector();
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Should be accessed through dependency injection.
	 * @deprecated
	 */
	@Deprecated
	public LogicLocator getLogicLocator() {
		return logicLocator;
	}

	/**
	 * Reads the vraptor.xml file. If not found, search for component classes
	 * class loading all of them.
	 * 
	 * Inits this configuration: overrides the viewmanager with the
	 * views.properties file
	 * 
	 * @throws ConfigException
	 * 
	 * @see org.vraptor.webapp.WebApplication#init()
	 */
	public void init() throws ConfigException {

		Configuration cfg = new Configuration(applicationContext, componentManager);
		cfg.load(this);

		// reads overriden views.properties
		InputStream viewProperties = DefaultWebApplication.class.getResourceAsStream("/views.properties");
		if (viewProperties != null) {
			try {
				// decorating regex view manager with overriden view manager and
				// then default view manager
				this.viewManager = new ViewsPropertiesReader().overrideViews(viewManager, viewProperties);
			} catch (IOException e) {
				LOG.warn("Error reading views.properties", e);
			}
		}
	}

	public ComponentManager getComponentManager() {
		return this.componentManager;
	}

	public ViewManager getDefaultViewManager() {
		return this.viewManager;
	}

	public ConverterManager getConverterManager() {
		return this.converterManager;
	}

	public void setViewManager(ViewManager viewManager) {
		this.viewManager = viewManager;
	}

	public PluginManager getPluginManager() {
		return this.pluginManager;
	}

	public Introspector getIntrospector() {
		return introspector;
	}
	
	public ComponentContainer getContainer() {
		return container;
	}

}
