package org.vraptor.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicRequest;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.component.ComponentType;
import org.vraptor.http.VRaptorServletRequest;
import org.vraptor.interceptor.ComponentLookupInterceptor;
import org.vraptor.interceptor.ExecuteLogicInterceptor;
import org.vraptor.interceptor.FlashScopeInterceptor;
import org.vraptor.interceptor.InjectionInterceptor;
import org.vraptor.interceptor.InterceptorDealer;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.interceptor.OutjectionInterceptor;
import org.vraptor.interceptor.RegisterAttributesInteceptor;
import org.vraptor.interceptor.SettingAndValidationInterceptor;
import org.vraptor.interceptor.ViewInterceptor;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.url.DefaultRequestInfo;
import org.vraptor.url.InvalidURLException;
import org.vraptor.url.RequestInfo;
import org.vraptor.view.ViewException;
import org.vraptor.view.ViewManager;

/**
 * VRaptor's engine.
 *
 * @author Guilherme Silveira
 * @author Paulo Silveira
 */
@SuppressWarnings("unchecked")
public class VRaptorExecution {

	private static final Logger LOG = Logger.getLogger(VRaptorExecution.class);

	private final Controller controller;

    private final WebRequest webRequest;

    private final ComponentType componentType;

    private final LogicMethod method;

    private final static Class<Interceptor>[] AFTER = new Class[] { ComponentLookupInterceptor.class,
			InjectionInterceptor.class, SettingAndValidationInterceptor.class, ExecuteLogicInterceptor.class,
			OutjectionInterceptor.class, ViewInterceptor.class };

	public VRaptorExecution(ComponentType componentType, LogicMethod method, Controller controller, WebRequest webRequest) {
        this.componentType = componentType;
        this.webRequest = webRequest;
		this.controller = controller;
		this.method = method;
	}

	public String execute() throws ComponentNotFoundException, LogicNotFoundException, ViewException,
			InterceptorInstantiationException, LogicException, InvalidURLException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Calling execute on " + webRequest.toString() + " / " + method.toString());
		}

		DefaultLogicDefinition definition = new DefaultLogicDefinition(null, method.getComponentType(), method);

		VRaptorServletRequest vraptorRequest = new VRaptorServletRequest(webRequest.getRequest(), controller.getWebApplication().getIntrospector());

		WebRequest wrappedWebRequest = new WebRequest(vraptorRequest, webRequest.getResponse(), webRequest.getContext());
		RequestInfo info = new DefaultRequestInfo(method.getComponentType().getName(), method.getName());
		LogicRequest logicRequest = new DefaultLogicRequest(info, wrappedWebRequest, definition);
		ViewManager viewManager = controller.getWebApplication().getContainer().getViewLocator().locate(webRequest.getRequest(), method, controller.getWebApplication().getDefaultViewManager());
		vraptorRequest.setAttribute(ViewManager.class.getName(), viewManager);

		// TODO: are these necessary? at least externalize
		// FIXME: vraptorRequest and logcRequest have a bidirection dependency
		vraptorRequest.setCurrentRequest(logicRequest);
		vraptorRequest.setAttribute("context", logicRequest);
		vraptorRequest.setAttribute("application", controller.getWebApplication().getApplicationContext());

		List<Interceptor> before = new ArrayList<Interceptor>();
		before.add(new RegisterAttributesInteceptor());
        before.add(new FlashScopeInterceptor());

		List<InterceptorType> fullInterceptors = new ArrayList<InterceptorType>(componentType.getInterceptors());
		for (Class<? extends Interceptor> type : AFTER) {
			fullInterceptors.add(InterceptorType.getType(type));
		}
		InterceptorDealer dealer = new InterceptorDealer(logicRequest, controller.getIntrospector(), before,
				fullInterceptors, new ArrayList<Interceptor>());
		InterceptorsLogicFlow logicFlow = new InterceptorsLogicFlow(logicRequest, dealer);

		logicFlow.execute();

		return logicFlow.getLogicRequest().getResult();
	}

}
