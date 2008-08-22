package org.vraptor.plugin.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.webapp.WebApplication;

public class MethodInterceptorPluginTest extends TestCase {

	private Mockery mockery;

	protected void setUp() throws Exception {
		super.setUp();
		mockery = new Mockery();
	}
	
	public void testInitWithAComponentThatShouldWrap() throws Exception {
		final WebApplication application = mockery.mock(WebApplication.class);
		mockery.checking(new Expectations() {
			{
				ComponentManager manager = mockery.mock(ComponentManager.class);
				ComponentType type = mockery.mock(ComponentType.class);
				LogicMethod method = mockery.mock(LogicMethod.class);
				
				one(application).getComponentManager();
				will(returnValue(manager));
				
				one(manager).getComponents();
				will(returnValue(new HashSet<ComponentType>(Arrays.asList(type))));
				
				Method someMethod = AComponent.class.getDeclaredMethod("someMethod");
				assertEquals("someMethod", someMethod.getName());
				one(method).getMetadata();
				will(returnValue(someMethod));
				
				one(type).getLogics();
				will(returnValue(Arrays.asList(method)));
				
				one(type).getInterceptors();
				will(returnValue(new ArrayList<InterceptorType>()));
			}
		});
		new MethodInterceptorPlugin().init(application);
		mockery.assertIsSatisfied();
	}
	private class AComponent {
		@InterceptedBy(MethodInterceptorInterceptor.class)
		void someMethod() {
			
		}
	}

}
