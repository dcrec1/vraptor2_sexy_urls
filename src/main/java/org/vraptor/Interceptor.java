package org.vraptor;

import org.vraptor.view.ViewException;

/**
 * Interceptor interface.
 * 
 * @author Guilherme Silveira
 */
public interface Interceptor {

	/**
	 * Intercepts calls to logic components
	 * @param logic	the logic flow
	 * @throws LogicException	some logic exception occurred
	 * @throws ViewException	some view exception occurred 
	 */
	void intercept(LogicFlow flow) throws LogicException,
			ViewException;

}
