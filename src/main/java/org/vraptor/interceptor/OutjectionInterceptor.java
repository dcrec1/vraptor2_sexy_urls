package org.vraptor.interceptor;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.introspector.Introspector;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.view.ViewException;

/**
 * Outjects.
 *
 * @author Guilherme Silveira
 */
public class OutjectionInterceptor implements Interceptor {

	private final Introspector introspector;

	public OutjectionInterceptor(Introspector introspector) {
		this.introspector = introspector;
	}

	public void intercept(LogicFlow flow) throws LogicException, ViewException {

		Object component = flow.getLogicRequest().getLogicDefinition().getComponent();

		try {
			introspector.outject(flow.getLogicRequest(), component, flow.getLogicRequest().getLogicDefinition().getComponentType());
		} catch (GettingException e) {
			throw new LogicException(e.getMessage(), e);
		} catch (MethodInvocationException e) {
			throw new LogicException(e);
		}

		flow.execute();

	}

}
