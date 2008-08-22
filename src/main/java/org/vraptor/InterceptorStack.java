package org.vraptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vraptor.interceptor.InterceptorType;
import org.vraptor.introspector.Introspector;
import org.vraptor.plugin.interceptor.ConcatenateInterceptorsLogicFlow;
import org.vraptor.view.ViewException;

/**
 * An interceptor that can be used to group common interceptors into one,
 * avoiding lots of classes inside an InterceptedBy annotation.
 *
 * @author Paulo Silveira
 * @author Guilherme Silveira
 * @since 2.3.1
 */
public class InterceptorStack implements Interceptor {

	private final List<InterceptorType> interceptors;

	/**
	 * Constructs an InterceptorStack with the given interceptors
	 *
	 * @param interceptors
	 */
	public InterceptorStack(Class<? extends Interceptor>... interceptorClasses) {
		if (interceptorClasses == null) {
			throw new NullPointerException();
		}

		this.interceptors = new ArrayList<InterceptorType>();

		for (Class<? extends Interceptor> interceptorClass : interceptorClasses) {
			InterceptorType type = InterceptorType.getType(interceptorClass);
			interceptors.add(type);
		}
	}

	/**
	 * Intercepts the logic flow with the given interceptors, respecting the
	 * order.
	 */
	public void intercept(LogicFlow realFlow) throws LogicException, ViewException {
		Introspector introspector = (Introspector) realFlow.getLogicRequest().getApplicationContext().getAttribute(
				Introspector.class.getName());
		new ConcatenateInterceptorsLogicFlow(interceptors, realFlow, introspector).execute();
	}

	public List<InterceptorType> getInterceptors() {
		return Collections.unmodifiableList(interceptors);
	}
}
