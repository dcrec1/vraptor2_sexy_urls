package org.vraptor.plugin;

import org.vraptor.VRaptorException;
import org.vraptor.webapp.WebApplication;

/**
 * A vraptor plugin.
 * 
 * @author Guilherme Silveira
 * 
 */
public interface VRaptorPlugin {

	/**
	 * Called after vraptor.xml has been completely parsed to give a chance for
	 * this plugin to do something in the web application
	 * 
	 * @param application
	 *            the web application
	 * @throws VRaptorException some problem that might occur, stops system startup
	 */
	void init(WebApplication application) throws VRaptorException;

}
