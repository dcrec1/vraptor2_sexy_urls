package org.vraptor.webapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.vraptor.view.OverridableViewManager;
import org.vraptor.view.ViewManager;

/**
 * Reads a view properties file and gives back a new ViewManager based on the
 * current one.
 */
public class ViewsPropertiesReader {

	private static final Logger LOG = Logger.getLogger(ViewsPropertiesReader.class);

	/**
	 * Creates a new view manager which overrides all view properties in the
	 * resource with their new values... old values will use the current
	 * manager's response
	 * 
	 * @param currentManager
	 *            default manager
	 * @param resource
	 *            properties resource
	 * @return the new view manager
	 * @throws IOException
	 */
	public ViewManager overrideViews(ViewManager currentManager, InputStream resource) throws IOException {
		OverridableViewManager manager = new OverridableViewManager(currentManager);
		Properties properties = new Properties();
		properties.load(resource);

		for (Object key : properties.keySet()) {
			manager.overrideView(key.toString(), properties.get(key).toString());
		}

		return manager;
	}

}
