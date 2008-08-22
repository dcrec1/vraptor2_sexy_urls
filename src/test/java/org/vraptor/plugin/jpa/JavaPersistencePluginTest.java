package org.vraptor.plugin.jpa;

import java.util.Arrays;
import java.util.HashSet;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.vraptor.AbstractTest;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentType;
import org.vraptor.webapp.WebApplication;

public class JavaPersistencePluginTest extends AbstractTest {

	private Mockery mockery;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mockery = new Mockery();
	}
	
	public void testWrapsAPersistenceContextComponent() throws Exception {
		final WebApplication application = mockery.mock(WebApplication.class);
		mockery.checking(new Expectations() {
			{
				ComponentType component = createComponentType(EntityManagerDependent.class);
				ComponentManager manager = mockery.mock(ComponentManager.class);
				
				one(application).getComponentManager();
				will(returnValue(manager));
				
				one(manager).getComponents();
				will(returnValue(new HashSet<ComponentType>(Arrays.asList(component))));
				
				one(manager).register(with(any(ComponentType.class)));
			}
		});
		new JavaPersistencePlugin().init(application);
		mockery.assertIsSatisfied();
	}
}
