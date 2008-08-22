package org.vraptor.plugin.interceptor;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.webapp.WebApplication;

/**
 * A method interceptor plugin. Allows users to specify the InterceptedBy
 * annotation on methods.
 *
 * @author Guilherme Silveira
 * @since 2.3.2
 */
public class MethodInterceptorPlugin implements VRaptorPlugin {

	private static final Logger LOG = Logger.getLogger(MethodInterceptorPlugin.class);

	public void init(WebApplication application) throws LogicNotFoundException {
		LOG.info("Initializing Method Interceptor Plugin");
		InterceptorType interceptorType = InterceptorType.getType(MethodInterceptorInterceptor.class);
		ComponentManager manager = application.getComponentManager();
		for (ComponentType component : manager.getComponents()) {
			if (shouldWrap(component)) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Wrapping " + component.getComponentClass().getName()
							+ " for method interceptor support.");
				}
				component.getInterceptors().add(interceptorType);
			}
		}
	}

	private boolean shouldWrap(ComponentType component) throws LogicNotFoundException {
		Collection<LogicMethod> logics = component.getLogics();
		for (LogicMethod logic : logics) {
			if (logic.getMetadata().isAnnotationPresent(InterceptedBy.class)) {
				return true;
			}
		}
		return false;
	}

}
