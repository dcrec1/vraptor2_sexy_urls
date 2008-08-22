package org.vraptor.plugin.interceptor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.RedirectException;
import org.vraptor.core.InterceptorsLogicFlow;
import org.vraptor.interceptor.InterceptorDealer;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.introspector.Introspector;
import org.vraptor.view.ViewException;

/**
 * A flow which concatenates a few interceptors in the middle of the current
 * flow.
 * 
 * @author Guilherme Silveira
 * @since 2.3.2
 */
public class ConcatenateInterceptorsLogicFlow implements LogicFlow {

	private final InterceptorsLogicFlow currentFlow;

	private final LogicFlow previousFlow;

	public ConcatenateInterceptorsLogicFlow(List<InterceptorType> interceptors, LogicFlow flow,
			Introspector introspector) {
		this.previousFlow = flow;
		InterceptorDealer dealer = new InterceptorDealer(flow.getLogicRequest(), introspector,
				new LinkedList<Interceptor>(), interceptors, new LinkedList<Interceptor>(Arrays
						.asList(new Interceptor[] { new ContinueToFlowInterceptor(previousFlow) })));
		this.currentFlow = new InterceptorsLogicFlow(flow.getLogicRequest(), dealer);
	}

	public void execute() throws ViewException, LogicException {
		currentFlow.execute();
	}

	public LogicRequest getLogicRequest() {
		return currentFlow.getLogicRequest();
	}

	public void redirect(String url) throws RedirectException {
		previousFlow.redirect(url);
	}

}
