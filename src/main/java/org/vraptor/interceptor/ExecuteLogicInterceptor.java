package org.vraptor.interceptor;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.annotations.Remotable;
import org.vraptor.component.LogicMethod;
import org.vraptor.view.ViewException;
import org.vraptor.view.ViewManager;

/**
 * The last interceptor: calls the logic method.
 *
 * @author Guilherme Silveira
 * @author Nico Steppat
 */
public class ExecuteLogicInterceptor implements Interceptor {

	private static final Logger LOG = Logger.getLogger(ExecuteLogicInterceptor.class);

	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		Object component = flow.getLogicRequest().getLogicDefinition().getComponent();
		LogicMethod logicMethod = flow.getLogicRequest().getLogicDefinition().getLogicMethod();

		if (LOG.isDebugEnabled()) {
			LOG.debug("executing business logic " + logicMethod);
		}

		Object[] parameters = flow.getLogicRequest().getLogicDefinition().getParameters();

		String result = logicMethod.execute(component, flow.getLogicRequest(), parameters);

		// TODO: an interceptor could have already set the result
		// should we check for it? since confirmResult throws exception when
		// already called
		flow.getLogicRequest().confirmResult(result);
		flow.execute();
	}

}
