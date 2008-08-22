package org.vraptor.test;

import org.vraptor.VRaptorException;
import org.vraptor.scope.SessionContext;

/**
 * A test context emulates an user session in
 * 
 * @author Guilherme Silveira
 * 
 */
public interface TestSession {

	/**
	 * Executes a url call based on some url. The parameters are strings like
	 * "a=b", "c=d".
	 * 
	 * @param relativeUrl
	 *            relative url to server
	 * @param parameters
	 *            the parameter list to be passed
	 * @return the logic result
	 */
	String execute(String relativeUrl, String... parameters)
			throws VRaptorException;

	SessionContext getSessionContext();

}
