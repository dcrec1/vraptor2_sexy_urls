package org.vraptor.plugin.spring;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.vraptor.introspector.Introspector;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.webapp.WebApplication;

/**
 * Spring plugin which reads the application bean configuration and allows it to
 * be used for VRaptor's dependencies injection.
 *
 * @author Guilherme Silveira
 * @author Fabio Patricio
 * @author Rafael Steil
 * @since 2.2.4
 */
public class SpringPlugin implements VRaptorPlugin {

	private static final String CONTEXT_KEY_DEFAULT_VALUE = "springContext";

	private static final String CONFIG_FILE_DEFAULT_VALUE = "/WEB-INF/classes/applicationContext.xml";

	protected static final String CONFIG_FILE_KEY = "configFile";

	protected static final String CONTEXT_KEY_KEY = "contextName";

	private static final Logger LOG = Logger.getLogger(SpringPlugin.class);

	private final Map<String, String> properties;

	public SpringPlugin(Map<String, String> properties) {
		this.properties = properties;
	}

	public void init(WebApplication application) {
		long startTime = System.currentTimeMillis();
		LOG.info("Loading Spring root WebApplicationContext");

		String configFile = this.applicationRoot(application)
				+ this.ensureStartWithSlash(this.getContextFilename(application));
		String contextName = this.getContextName(application);

		if (LOG.isInfoEnabled()) {
			LOG.info("Configuration file for Spring is " + configFile);
			LOG.info("Context name for Spring is " + contextName);
		}

		ApplicationContext context = new FileSystemXmlApplicationContext(configFile);
		application.getApplicationContext().setAttribute(contextName, context);
		application.getApplicationContext().setAttribute(ApplicationContext.class.getName(), context);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Published root WebApplicationContext [" + context + "] as ServletContext attribute");
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("Using context class [" + context.getClass().getName() + "] for root ApplicationContext");
			long elapsedTime = System.currentTimeMillis() - startTime;
			LOG.info("Root ApplicationContext: initialization completed in " + elapsedTime + " ms");
		}

		Introspector introspector = application.getIntrospector();
		SpringProvider provider = new SpringProvider(introspector.getBeanProvider(), context);
		LOG.debug("Registering new spring provider with the introspector");
		introspector.setBeanProvider(provider);
	}

	private String ensureStartWithSlash(String s) {
		char firstChar = s.charAt(0);

		if (firstChar != '/' && firstChar != '\\') {
			s = "/" + s;
		}

		return s;
	}

	/**
	 * Returns the application's root directory, without the trailing slash.
	 *
	 * @param application
	 * @return
	 */
	private String applicationRoot(WebApplication application) {
		String path = application.getApplicationContext().getRealPath("/");
		char lastChar = path.charAt(path.length() - 1);

		if (lastChar == '/' || lastChar == '\\') {
			path = path.substring(0, path.length() - 1);
		}

		return path;
	}

	private String getContextName(WebApplication application) {
		return getProperty(CONTEXT_KEY_KEY, CONTEXT_KEY_DEFAULT_VALUE);
	}

	private String getProperty(String key, String defaultValue) {
		return properties.containsKey(key) ? properties.get(key) : defaultValue;
	}

	private String getContextFilename(WebApplication application) {
		return getProperty(CONFIG_FILE_KEY, CONFIG_FILE_DEFAULT_VALUE);
	}
}
