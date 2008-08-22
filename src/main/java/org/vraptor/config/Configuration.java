package org.vraptor.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import org.apache.log4j.Logger;
import org.vraptor.VRaptorFilter;
import org.vraptor.annotations.Component;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.scope.ApplicationContext;
import org.vraptor.webapp.WebApplication;

/**
 * Base class for vraptor configuration.
 *
 * @author Guilherme Silveira
 */
public class Configuration {

	private static final String VRAPTOR_XML_LOCATION = "/";

	private static final String WEBINF_CLASSES_DIRECTORY = "/WEB-INF/classes/";

	private static final Logger LOG = Logger.getLogger(Configuration.class);

	private static final String DOT_CLASS = ".class";

	private final ApplicationContext context;

	private final ComponentManager componentManager;

	/**
	 * Checks if className is a valid Component (annotated)
	 *
	 * @param className
	 * @return
	 * @throws IllegalArgumentException
	 *             if className cannot be loaded
	 */
	private boolean classIsComponent(String className) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Will check class " + className);
			}
			Class<?> type = Class.forName(className, false, this.getClass().getClassLoader());
			return type.isAnnotationPresent(Component.class);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("cant check if %s is a class, unable to load it: " + e,
					className), e);
		}
	}

	/**
	 * Parses a String like
	 * <code>/WEB-INF/classes/br/com/caelum/Test.class to</code>
	 * <code>br.com.caelum.Test.class</code>
	 *
	 * @param directory
	 * @param name
	 * @return
	 */
	String parseClassName(String directory, String name) {
		if (!name.startsWith(directory)) {
			throw new IllegalArgumentException();
		}
		name = name.substring(directory.length(), name.length() - DOT_CLASS.length());
		name = name.replaceAll("/|\\\\", ".");
		// bug fix for cargo
		name = name.replaceAll("\\.\\.", ".");
		return name;
	}

	public Configuration(ApplicationContext applicationContext, ComponentManager componentManager) {
		this.context = applicationContext;
		this.componentManager = componentManager;
	}

	private void readDirectory() throws ConfigException {
		LOG.info("loading components from WEB-INF/classes");
		try {
			readDirectory(WEBINF_CLASSES_DIRECTORY);
		} catch (InvalidComponentException e) {
			throw new ConfigException("cannot load classes", e);
		}
	}

	/**
	 * Reads all .class files from <code>directory</code> looking for
	 * annotated vraptor classes (@Component).
	 *
	 * @param directory
	 * @throws InvalidComponentException
	 */
	private void readDirectory(String directory) throws InvalidComponentException {
		// TODO better code? shale does something similar
		// TODO check jetty compatibility
		@SuppressWarnings("unchecked")
		Set<String> items = context.getResourcePaths(directory);

		if (items == null) {
			LOG.info(directory + " not found");
			return;
		}

		for (String item : items) {
			File file = new File(context.getRealPath(item));
			if (file.isDirectory()) {
				readDirectory(item);
			} else if (item.endsWith(DOT_CLASS)) {
				String className = parseClassName(WEBINF_CLASSES_DIRECTORY, item);
				if (classIsComponent(className)) {
					if (LOG.isInfoEnabled()) {
						LOG.info(String.format("component found %s: %s", directory, className));
					}
					this.componentManager.register(className);
				}
			}
		}
	}

	public void load(WebApplication web) throws ConfigException {
		boolean autoDiscover = true;
		autoDiscover &= loadXml(web, "");
		autoDiscover &= loadXml(web, "-vatx");
		if(autoDiscover) {
			autoDiscoverComponents();
		}
	}

	private boolean loadXml(WebApplication web, String suffix) throws ConfigException {
		InputStream file = getXMLFile(suffix);
		if (file == null) {
			LOG.info("No (optional) vraptor" + suffix + ".xml found.");
			return true;
		}
		try {
			XMLConfigurationLoader loader = new XMLConfigurationLoader();
			VRaptorConfiguration config = loader.read(new InputStreamReader(file));
			config.readManagers(web);
			config.readComponents(web);
			return config.isAutoDiscover();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				throw new ConfigException(e.getMessage(), e);
			}
		}
	}

	private void autoDiscoverComponents() throws ConfigException {
		long initTime = System.currentTimeMillis();
		readDirectory();
		if (LOG.isDebugEnabled()) {
			long totalTime = System.currentTimeMillis() - initTime;
			LOG.debug(String.format("auto-discover total time: %d ms", totalTime));
		}
	}

	private InputStream getXMLFile(String suffix) {
		return VRaptorFilter.class.getResourceAsStream(VRAPTOR_XML_LOCATION + "vraptor" + suffix + ".xml");
	}

}
