package org.vraptor;

import java.util.Arrays;
import java.util.List;

import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.core.InterceptorsLogicFlow;
import org.vraptor.core.MockLogicFlow;
import org.vraptor.introspector.BasicIntrospector;
import org.vraptor.introspector.Introspector;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.view.ViewException;

public class InterceptorStackTest extends AbstractTest {

	@SuppressWarnings("unused")
	public static class Interceptor1 implements Interceptor {
		static int qtd = 0;
		static Interceptor1 last;
		{
			qtd++;
			last = this;
		}

		@Out
		private int x = 5;

		public boolean executed;

		public boolean executing;

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			executing = true;
			flow.execute();
			executed = true;
		}
	}

	@SuppressWarnings("unused")
	public static class Interceptor2 implements Interceptor {
		static int qtd = 0;
		static Interceptor2 last;
		{
			qtd++;
			last = this;
		}
		@Out
		private int y = 7;

		private boolean executed;

		public boolean executing;

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			executing = true;
			flow.execute();
			executed = true;
		}
	}

	public static class Interceptor3 implements Interceptor {
		private boolean executed;

		@Out
		private int z = 9;

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			flow.execute();
			executed = true;
		}
	}

	@SuppressWarnings("unused")
	public static class EndInterceptor implements Interceptor {
		@In
		private int x;

		@In
		private int y;

		@In
		private int z;

		private boolean executed;

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			executed = true;
		}
	}

	public static class VerifierInterceptor implements Interceptor {

		private boolean executed;

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			assertFalse(Interceptor1.last.executed);
			assertFalse(Interceptor2.last.executed);
			assertTrue(Interceptor1.last.executing);
			assertTrue(Interceptor2.last.executing);
			executed = true;
		}
	}

	public static class Stack extends InterceptorStack {
		public Stack() {
			super(Interceptor1.class, Interceptor2.class, Interceptor3.class);
		}
	}

	@SuppressWarnings("unused")
	public static class LogicForStacking {
		@In
		private int x;

		@In
		private int y;
	}

	public void testStackWithTwoInterceptors() throws LogicException, ViewException, InvalidComponentException {
		DefaultLogicRequest logicRequest = createLogicRequest();
		MockLogicFlow flow = new MockLogicFlow(logicRequest);
		logicRequest.getApplicationContext().setAttribute(Introspector.class.getName(), new BasicIntrospector());

		Stack stack = new Stack();
		int qtd1 = Interceptor1.qtd, qtd2 = Interceptor2.qtd;
		stack.intercept(flow);
		assertEquals(qtd1+1, Interceptor1.qtd);
		assertEquals(qtd2+1, Interceptor2.qtd);
		assertTrue(Interceptor1.last.executed);
		assertTrue(Interceptor2.last.executed);
	}

	public void testStackWithTwoInterceptorsWithCommonInterceptors() throws LogicException, ViewException,
			InvalidComponentException {
		DefaultLogicRequest request = createLogicRequest();
		Introspector introspector = new BasicIntrospector();
		request.getApplicationContext().setAttribute(Introspector.class.getName(), introspector);

		Interceptor3 outOfStack = new Interceptor3();
		Stack stack = new Stack();
		EndInterceptor endInterceptor = new EndInterceptor();

		List<Interceptor> interceptors = Arrays.asList(outOfStack, stack, endInterceptor);
		InterceptorsLogicFlow flow = new InterceptorsLogicFlow(request, createInterceptorDealer(request, introspector, interceptors));
		int qtd1 = Interceptor1.qtd, qtd2 = Interceptor2.qtd;
		flow.execute();
		assertEquals(qtd1+1, Interceptor1.qtd);
		assertEquals(qtd2+1, Interceptor2.qtd);
		assertTrue(Interceptor1.last.executed);
		assertTrue(Interceptor2.last.executed);

		assertTrue(outOfStack.executed);
		assertTrue(endInterceptor.executed);
	}

	public void testInterceptorsOrderStackWithTwoInterceptorsWithCommonInterceptors() throws LogicException,
			ViewException, InvalidComponentException {
		DefaultLogicRequest request = createLogicRequest();
		Introspector introspector = new BasicIntrospector();
		request.getApplicationContext().setAttribute(Introspector.class.getName(), introspector);

		Interceptor3 outOfStack = new Interceptor3();
		Stack stack = new Stack();
		VerifierInterceptor verifierInterceptor = new VerifierInterceptor();

		List<Interceptor> interceptors = Arrays.asList(outOfStack, stack, verifierInterceptor);
		InterceptorsLogicFlow flow = new InterceptorsLogicFlow(request, createInterceptorDealer(request, introspector, interceptors));
		int qtd1 = Interceptor1.qtd, qtd2 = Interceptor2.qtd;
		flow.execute();
		assertEquals(qtd1+1, Interceptor1.qtd);
		assertEquals(qtd2+1, Interceptor2.qtd);
		assertTrue(Interceptor1.last.executed);
		assertTrue(Interceptor2.last.executed);

		assertTrue(outOfStack.executed);
		assertTrue(verifierInterceptor.executed);
	}

	public void testStackOutjectionToInjectInCommonInterceptors() throws LogicException, ViewException,
			InvalidComponentException {
		DefaultLogicRequest request = createLogicRequest();
		Introspector introspector = new BasicIntrospector();
		request.getApplicationContext().setAttribute(Introspector.class.getName(), introspector);

		Interceptor3 outOfStack = new Interceptor3();
		Stack stack = new Stack();
		EndInterceptor endInterceptor = new EndInterceptor();

		List<Interceptor> interceptors = Arrays.asList(outOfStack, stack, endInterceptor);
		InterceptorsLogicFlow flow = new InterceptorsLogicFlow(request, createInterceptorDealer(request, introspector, interceptors));
		int qtd1 = Interceptor1.qtd, qtd2 = Interceptor2.qtd;
		flow.execute();
		assertEquals(qtd1+1, Interceptor1.qtd);
		assertEquals(qtd2+1, Interceptor2.qtd);
		assertTrue(Interceptor1.last.executed);
		assertTrue(Interceptor2.last.executed);

		assertEquals(outOfStack.z, endInterceptor.z);
	}

}
