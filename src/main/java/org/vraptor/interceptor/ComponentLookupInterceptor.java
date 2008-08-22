package org.vraptor.interceptor;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.core.DefaultLogicDefinition;
import org.vraptor.introspector.BeanProvider;
import org.vraptor.introspector.Introspector;
import org.vraptor.scope.Context;
import org.vraptor.scope.ScopeType;
import org.vraptor.view.ViewException;

/**
 * Looks up for the component and registers it in the logic context.
 *
 * @author Guilherme Silveira
 */
public class ComponentLookupInterceptor implements Interceptor {

	private final BeanProvider provider;

	private final static Logger LOG = Logger.getLogger(ComponentLookupInterceptor.class);

	public ComponentLookupInterceptor(Introspector introspector) {
		this.provider = introspector.getBeanProvider();
	}

	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		try {

			ComponentType type = flow.getLogicRequest().getLogicDefinition().getComponentType();
			LogicMethod method = flow.getLogicRequest().getLogicDefinition().getLogicMethod();

			LogicRequest request = flow.getLogicRequest();
			String key = type.getKey();

			Context scope = type.getScope().getContext(request);

			Object componentObject;
			if (scope.hasAttribute(key)) {
				componentObject = scope.getAttribute(key);
			} else {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Instantiating class " + type.getComponentClass().getName());
				}
				componentObject = type.getConstructor().newInstance(request, provider);
			}

			scope.setAttribute(key, componentObject);

			request.setLogicDefinition(new DefaultLogicDefinition(componentObject, type, method));

			flow.execute();

			// if either logic or request, should be removed
			if (type.getDestroyLogicName().equals(request.getRequestInfo().getLogicName())
					|| type.getScope().equals(ScopeType.REQUEST)) {
				// remove it from request|logic due to server-side redirect problem
				scope.removeAttribute(key);
			}

		} catch (ComponentInstantiationException e) {
			throw new LogicException(e.getMessage(), e);
		}
	}

}
