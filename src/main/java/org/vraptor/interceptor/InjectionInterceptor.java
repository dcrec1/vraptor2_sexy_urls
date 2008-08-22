package org.vraptor.interceptor;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.introspector.Introspector;
import org.vraptor.reflection.SettingException;
import org.vraptor.view.ViewException;

/**
 * Interceptor responsible for injection of dependencies on In annotations.
 *
 * @author Guilherme Silveira
 */
public class InjectionInterceptor implements Interceptor {

	private final Introspector introspector;

	public InjectionInterceptor(Introspector introspector) {
		this.introspector = introspector;
	}

	public void intercept(LogicFlow flow) throws LogicException, ViewException {

		LogicRequest request = flow.getLogicRequest();

		try {

			Object component = request.getLogicDefinition().getComponent();

			introspector.inject(flow.getLogicRequest().getLogicDefinition().getComponentType().getInAnnotations(), component, request);

		} catch (ComponentInstantiationException e) {
			throw new LogicException(e);
		} catch (SettingException e) {
			throw new LogicException(e);
		}

		flow.execute();

	}

}
