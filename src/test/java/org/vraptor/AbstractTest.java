package org.vraptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.jmock.MockObjectTestCase;
import org.vraptor.component.ComponentContainer;
import org.vraptor.component.ComponentType;
import org.vraptor.component.DefaultComponentContainer;
import org.vraptor.component.DefaultLogicMethod;
import org.vraptor.component.DefaultLogicMethodFactory;
import org.vraptor.component.DefaultParameterInfoProvider;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.MethodParameter;
import org.vraptor.converter.ConverterManager;
import org.vraptor.converter.SimpleConverterManager;
import org.vraptor.core.WebRequest;
import org.vraptor.interceptor.InterceptorDealer;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.introspector.Introspector;
import org.vraptor.scope.ApplicationContext;
import org.vraptor.scope.DefaultApplicationContext;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.test.MockedHttpSession;
import org.vraptor.test.MockedRequest;
import org.vraptor.test.MockedResponse;
import org.vraptor.test.MockedServletContext;
import org.vraptor.url.DefaultRequestInfo;

/**
 * Base test class with factory methods.
 *
 * @author Guilherme Silveira
 */
public abstract class AbstractTest extends MockObjectTestCase {
	
	protected ComponentContainer registry;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.registry = new DefaultComponentContainer();
	}

	protected WebRequest createWebRequest() {
		return new WebRequest(createRequest(), createResponse(), createServletContext());
	}

	protected MockedRequest createRequest(String... parameters) {
		MockedHttpSession session = createSession();
		return new MockedRequest("component.logic.logic", session, parameters);
	}

	protected MockedHttpSession createSession() {
		return createSession(createServletContext());
	}

	protected MockedHttpSession createSession(ServletContext servletContext) {
		return new MockedHttpSession(servletContext);
	}

	protected DefaultLogicRequest createLogicRequest() {
		return createLogicRequest("component", "logic");
	}

	protected DefaultLogicRequest createLogicRequest(String component, String logic) {
		return createLogicRequest(component, logic, createRequest(), createResponse(), createServletContext());
	}

	protected DefaultLogicRequest createLogicRequest(String component, String logic, MockedRequest request,
			HttpServletResponse response, ServletContext context) {
		return new DefaultLogicRequest(new DefaultRequestInfo(component, logic), new WebRequest(request, response,
				context));
	}

	protected MockedServletContext createServletContext() {
		return new MockedServletContext();
	}

	protected ApplicationContext createApplicationContext() {
		return new DefaultApplicationContext(createServletContext());
	}

	protected HttpServletResponse createResponse() {
		return new MockedResponse();
	}

	protected <T> Method getMethod(Class<T> clazz, String methodName, Class... parameterTypes) throws SecurityException,
			NoSuchMethodException {
		return clazz.getDeclaredMethod(methodName, parameterTypes);
	}

	protected ConverterManager createConverterManager() {
		ConverterManager manager = new SimpleConverterManager();
		return manager;
	}

	protected LogicMethod createLogicMethod(Method method) throws InvalidComponentException {
		return new DefaultLogicMethod(registry.getValidationErrorsFactory(), method.getName(), method,null, new ArrayList<MethodParameter>());
	}

	/**
	 * Creates a method request.
	 */
	protected DefaultLogicMethod createLogicMethod(Class<?> type, String method) throws InvalidComponentException {
		Method[] methods = type.getDeclaredMethods();
		for (Method m : methods) {
			if (m.getName().equals(method)) {
				return new DefaultLogicMethodFactory(registry.getValidationErrorsFactory(), new DefaultParameterInfoProvider()).create(method, type, m);
			}
		}
		fail("Desired method was not found");
		return null;
	}

	protected InterceptorDealer createInterceptorDealer(DefaultLogicRequest logicRequest, Introspector introspector,
			List<Interceptor> before) {
		return new InterceptorDealer(logicRequest, introspector, before, new LinkedList<InterceptorType>(),
				new LinkedList<Interceptor>());
	}

	protected <T> ComponentType createComponentType(Class<T> type) throws InvalidComponentException {
		return registry.getComponentManager().getComponentType(type);
	}

}
