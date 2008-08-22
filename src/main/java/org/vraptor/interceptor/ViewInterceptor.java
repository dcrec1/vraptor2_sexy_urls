package org.vraptor.interceptor;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.view.ViewException;
import org.vraptor.view.ViewManager;

/**
 * The last interceptor of a LogicFlow. This one does not call flow.execute(),
 * since all of our interceptors are done!
 *
 * @author Paulo Silveira
 * @author Guilherme Silveira
 */
public class ViewInterceptor implements Interceptor {

	private static final Logger LOG = Logger.getLogger(ViewInterceptor.class);

	private final ViewManager viewManager;

	public ViewInterceptor(ViewManager viewManager) {
		this.viewManager = viewManager;
	}

	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		LogicRequest request = flow.getLogicRequest();

		// retrieves the result code, probably set by ExecutionLogicInterceptor
		String result = request.getResult();

		if (flow.getLogicRequest().getLogicDefinition().getLogicMethod().shouldRedirect()) {
			LOG.debug("last interception: ready to forward with result " + result);
			viewManager.forward(request, result);
		} else {
			LOG.debug("No view");
		}
	}
}
