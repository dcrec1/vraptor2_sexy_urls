package org.vraptor.plugin.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.introspector.Introspector;
import org.vraptor.view.ViewException;

/**
 * A method interceptor capability.
 *
 * @author Guilherme Silveira
 * @since 2.3.2
 */
public class MethodInterceptorInterceptor implements Interceptor {

	private final Introspector introspector;

	public MethodInterceptorInterceptor(Introspector introspector) {
		this.introspector = introspector;
	}

	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		Method method = flow.getLogicRequest().getLogicDefinition().getLogicMethod().getMetadata();
		if (method.isAnnotationPresent(InterceptedBy.class)) {
			// injects a few more interceptors here before finishing
			InterceptedBy annotation = method.getAnnotation(InterceptedBy.class);
			List<InterceptorType> interceptors = new ArrayList<InterceptorType>();
			for (Class<? extends Interceptor> interceptorType : annotation.value()) {
				interceptors.add(InterceptorType.getType(interceptorType));
			}
			ConcatenateInterceptorsLogicFlow newFlow = new ConcatenateInterceptorsLogicFlow(interceptors, flow, introspector);
			newFlow.execute();
		} else {
			flow.execute();
		}
	}

}
