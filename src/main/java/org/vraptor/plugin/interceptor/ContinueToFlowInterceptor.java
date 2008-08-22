package org.vraptor.plugin.interceptor;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.view.ViewException;

/**
 * An interceptor which redirects the current flow to another one.
 * 
 * @author Guilherme Silveira
 * @version 2.3.1
 */
public class ContinueToFlowInterceptor implements Interceptor {

	private final LogicFlow toFlow;

	/**
	 * @param toFlow
	 *            the flow to redirect to
	 */
	public ContinueToFlowInterceptor(LogicFlow toFlow) {
		this.toFlow = toFlow;
	}

	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		toFlow.execute();
	}

}
