package org.vraptor.plugin.interceptor;

import java.util.Arrays;

import org.vraptor.AbstractTest;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.core.MockLogicFlow;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.introspector.BasicIntrospector;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.view.ViewException;

public class ConcatenateInterceptorsLogicFlowTest extends AbstractTest {

	public static class MyInterceptor implements Interceptor {
		static int qtd = 0;

		static MyInterceptor last;

		private boolean executed;
		{
			qtd++;
			last = this;
		}

		public void intercept(LogicFlow flow) throws LogicException, ViewException {
			flow.execute();
			this.executed = true;
		}
	}

	public void testContinuesTheFlowAfterTheAddedInterceptor() throws ViewException, LogicException {
		DefaultLogicRequest request = createLogicRequest();
		MockLogicFlow flow = new MockLogicFlow(request);
		InterceptorType interceptor = InterceptorType.getType(MyInterceptor.class);
		ConcatenateInterceptorsLogicFlow newFlow = new ConcatenateInterceptorsLogicFlow(Arrays
				.asList(new InterceptorType[] { interceptor }), flow, new BasicIntrospector());
		newFlow.execute();
		assertTrue(flow.isExecuted());
	}

	public void testExecutesTheRequestInterceptor() throws ViewException, LogicException {
		DefaultLogicRequest request = createLogicRequest();
		MockLogicFlow flow = new MockLogicFlow(request);
		InterceptorType interceptor = InterceptorType.getType(MyInterceptor.class);
		ConcatenateInterceptorsLogicFlow newFlow = new ConcatenateInterceptorsLogicFlow(Arrays
				.asList(new InterceptorType[] { interceptor }), flow, new BasicIntrospector());
		int qtd = MyInterceptor.qtd;
		newFlow.execute();
		assertEquals(qtd + 1, MyInterceptor.qtd);
		assertTrue(MyInterceptor.last.executed);
	}

}
