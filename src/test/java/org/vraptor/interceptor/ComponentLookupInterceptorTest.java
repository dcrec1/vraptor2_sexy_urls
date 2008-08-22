package org.vraptor.interceptor;

import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.Component;
import org.vraptor.component.BeanConstructor;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.ComponentTypeWrapper;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.core.DefaultLogicDefinition;
import org.vraptor.core.MockLogicFlow;
import org.vraptor.introspector.BasicIntrospector;
import org.vraptor.introspector.BeanProvider;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.scope.ScopeType;
import org.vraptor.view.ViewException;

public class ComponentLookupInterceptorTest extends AbstractTest {

	public static class TestOk {
		static int lastCount = 0;
		{
			lastCount++;
		}

	}

	@Component(scope = ScopeType.SESSION)
	public static class ScopedTestOk {

	}

	public void testInstantiates() throws InvalidComponentException, LogicException, ViewException {

		DefaultLogicRequest request = createLogicRequest();
		request.setLogicDefinition(new DefaultLogicDefinition(null, createComponentType(TestOk.class), null));

		MockLogicFlow flow = new MockLogicFlow(request);
		ComponentLookupInterceptor interceptor = new ComponentLookupInterceptor(new BasicIntrospector());
		interceptor.intercept(flow);
		assertTrue(flow.isExecuted());
		Object obj = request.getLogicDefinition().getComponent();
		assertEquals(obj.getClass(), TestOk.class);
	}

	public static class TestNotOk {
		public TestNotOk() {
			throw new RuntimeException();
		}
	}

	public void testImpossibilityToInstantiate() throws InvalidComponentException, ViewException {
		DefaultLogicRequest request = createLogicRequest();
		request.setLogicDefinition(new DefaultLogicDefinition(null, createComponentType(TestNotOk.class), null));
		MockLogicFlow flow = new MockLogicFlow(request);
		ComponentLookupInterceptor interceptor = new ComponentLookupInterceptor(new BasicIntrospector());
		try {
			interceptor.intercept(flow);
			fail();
		} catch (LogicException e) {
			// ok
		}
	}

	public void testFindsTheComponentInTheCorrectKey() throws InvalidComponentException, LogicException, ViewException {
		ComponentType type = createComponentType(TestOk.class);
		DefaultLogicRequest request = createLogicRequest();
		request.setLogicDefinition(new DefaultLogicDefinition(null, type, null));
		TestOk original = new TestOk();
		int last = TestOk.lastCount;
		request.getRequestContext().setAttribute(type.getKey(), original);
		MockLogicFlow flow = new MockLogicFlow(request);
		ComponentLookupInterceptor interceptor = new ComponentLookupInterceptor(new BasicIntrospector());
		interceptor.intercept(flow);
		assertTrue(flow.isExecuted());
		assertEquals(last, TestOk.lastCount);
	}

	public void testSetsTheComponentInTheCorrectScope() throws InvalidComponentException, LogicException, ViewException {
		ComponentType type = createComponentType(ScopedTestOk.class);
		DefaultLogicRequest request = createLogicRequest("", "");
		request.setLogicDefinition(new DefaultLogicDefinition(null, type, null));
		MockLogicFlow flow = new MockLogicFlow(request);
		ComponentLookupInterceptor interceptor = new ComponentLookupInterceptor(new BasicIntrospector());
		interceptor.intercept(flow);
		assertTrue(flow.isExecuted());
		Object obj = request.getSessionContext().getAttribute(type.getKey());
		assertEquals(obj.getClass(), ScopedTestOk.class);
	}

	public void testRemovesTheComponentAfterDestroy() throws InvalidComponentException, LogicException, ViewException {
		ComponentType type = createComponentType(TestOk.class);
		DefaultLogicRequest request = createLogicRequest();
		request.setLogicDefinition(new DefaultLogicDefinition(null, type, null));
		MockLogicFlow flow = new MockLogicFlow(request);
		ComponentLookupInterceptor interceptor = new ComponentLookupInterceptor(new BasicIntrospector());
		interceptor.intercept(flow);
		assertTrue(flow.isExecuted());
		assertFalse(request.getRequestContext().hasAttribute(type.getKey()));
	}

	public void testUsesTheConstructorNewInstanceForTheNewComponent() throws InvalidComponentException, LogicException,
			ViewException {

		ComponentType type = createComponentType(TestOk.class);

		final TestOk instance = new TestOk();
		final BeanConstructor constructor = new BeanConstructor() {

			public Object newInstance(LogicRequest request, BeanProvider provider)
					throws ComponentInstantiationException {
				return instance;
			}

		};
		type = new ComponentTypeWrapper(type) {
			@Override
			public BeanConstructor getConstructor() {
				return constructor;
			}
		};

		DefaultLogicRequest request = createLogicRequest("", "");
		request.setLogicDefinition(new DefaultLogicDefinition(null, type, null));
		MockLogicFlow flow = new MockLogicFlow(request);
		ComponentLookupInterceptor interceptor = new ComponentLookupInterceptor(new BasicIntrospector());
		interceptor.intercept(flow);
		assertTrue(flow.isExecuted());
		assertEquals(instance, flow.getLogicRequest().getLogicDefinition().getComponent());

	}

}
