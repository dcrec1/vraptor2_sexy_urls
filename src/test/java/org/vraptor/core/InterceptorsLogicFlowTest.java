package org.vraptor.core;

import java.util.Arrays;
import java.util.List;

import org.vraptor.AbstractTest;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.introspector.BasicIntrospector;
import org.vraptor.introspector.Introspector;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.view.ViewException;

public class InterceptorsLogicFlowTest extends AbstractTest {

	public static class Interceptor1 implements Interceptor {
		private boolean executed;

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			executed = true;
			flow.execute();
		}
	}

	public static class Interceptor2 implements Interceptor {
		private boolean executed;

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			executed = true;
			flow.execute();
		}
	}

	public static class EndInterceptor implements Interceptor {
		private boolean executed;

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			executed = true;
			// THIS ONE DOES NOT CONTINUE THE CHAIN!
		}
	}

	public static class Interceptor3 implements Interceptor {
		boolean executed;

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			executed = true;
			flow.execute();
		}
	}

	public void testLogicFlowWithSomeInterceptors() throws ViewException, LogicException {
		DefaultLogicRequest request = createLogicRequest();
		Introspector introspector = new BasicIntrospector();

		Interceptor1 i1 = new Interceptor1();
		EndInterceptor end = new EndInterceptor();
		List<Interceptor> interceptors = Arrays.asList(i1, end);
		InterceptorsLogicFlow flow = new InterceptorsLogicFlow(request, createInterceptorDealer(request,introspector,interceptors));
		flow.execute();

		assertTrue(i1.executed);
		assertTrue(end.executed);
	}

	public void testLogicFlowWithInterceptorNotContinuingTheFlow() throws ViewException, LogicException {
		DefaultLogicRequest request = createLogicRequest();
		Introspector introspector = new BasicIntrospector();

		Interceptor1 i1 = new Interceptor1();
		EndInterceptor end = new EndInterceptor();
		Interceptor2 i2 = new Interceptor2();
		List<Interceptor> interceptors = Arrays.asList(i1, end, i2);
		InterceptorsLogicFlow flow = new InterceptorsLogicFlow(request, createInterceptorDealer(request,introspector,interceptors));
		flow.execute();

		assertTrue(i1.executed);
		assertTrue(end.executed);
		assertFalse(i2.executed);
	}

	public void testLogicFlowWithInterceptorsNotEndingTheFlow() throws ViewException, LogicException {
		DefaultLogicRequest request = createLogicRequest();
		Introspector introspector = new BasicIntrospector();

		Interceptor1 i1 = new Interceptor1();

		Interceptor2 i2 = new Interceptor2();
		List<Interceptor> interceptors = Arrays.asList(i1, i2);
		InterceptorsLogicFlow flow = new InterceptorsLogicFlow(request, createInterceptorDealer(request,introspector,interceptors));
		try {
			flow.execute();
			fail();
		} catch (IllegalStateException e) {
		}
	}

}
