package org.vraptor.core;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.vraptor.VRaptorException;
import org.vraptor.config.ConfigException;
import org.vraptor.introspector.Introspector;
import org.vraptor.view.ViewManager;
import org.vraptor.webapp.DefaultWebApplication;
import org.vraptor.webapp.WebApplication;

/**
 * VRaptor's controller factory.
 *
 * @author Guilherme Silveira
 *
 */
public class ControllerFactory {

	private static final Logger LOG = Logger.getLogger(ControllerFactory.class);

	/**
	 * VRaptor's controller factory.
	 *
	 * @param appContext
	 *            the servlet context
	 * @return the new vraptor controller
	 * @throws ConfigException
	 *             configuration exception
	 */
	public Controller configure(ServletContext appContext) throws ConfigException {
		LOG.info("VRaptor initializing ...");

		long start = System.currentTimeMillis();
		WebApplication app = new DefaultWebApplication(appContext);
		app.init();
		Controller controller = new DefaultController(app);
		appContext.setAttribute("webApplication", app);
		appContext.setAttribute(WebApplication.class.getName(), app);
		appContext.setAttribute(ViewManager.class.getName(), app.getDefaultViewManager());
		appContext.setAttribute(Introspector.class.getName(), app.getIntrospector());
		try {
			app.getPluginManager().init(app);
		} catch (VRaptorException e) {
			throw new ConfigException("Unable to startup some plugin.", e);
		}
		long buildTime = System.currentTimeMillis() - start;

		LOG.info("VRaptor init time " + (buildTime / 1000.0) + " seconds");
		return controller;
	}

}
