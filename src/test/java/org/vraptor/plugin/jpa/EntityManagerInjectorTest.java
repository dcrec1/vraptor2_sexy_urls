/**
 *
 */
package org.vraptor.plugin.jpa;

import javax.persistence.EntityManager;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.component.BeanConstructor;
import org.vraptor.component.DefaultLogicMethod;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.core.DefaultLogicDefinition;
import org.vraptor.introspector.BeanProvider;
import org.vraptor.scope.DefaultLogicRequest;

/**
 * @author Fabio Correia Kung
 *
 */
public class EntityManagerInjectorTest extends AbstractTest {

	private EntityManagerIntrospector introspector;

	private BeanProvider beanProvider;

	private EntityManager entityManager;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.introspector = new EntityManagerIntrospector();
		this.beanProvider = (BeanProvider) mock(BeanProvider.class).proxy();
		this.entityManager = (EntityManager) mock(EntityManager.class).proxy();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.introspector = null;
		this.beanProvider = null;
		this.entityManager = null;
	}

	public void testInjectsEntityManagerOnDependendComponentCreation() throws Exception {
		EntityManagerDependent component = new EntityManagerDependent();
		DefaultLogicRequest logicRequest = createLogicRequest(component);
		logicRequest.setLogicDefinition(new DefaultLogicDefinition(component, createComponentType(EntityManagerDependent.class), null));

		// expects delegation
		Mock constructorMock = mock(BeanConstructor.class);
		BeanConstructor wrapped = (BeanConstructor) constructorMock.proxy();
		constructorMock.expects(once()).method("newInstance").with(same(logicRequest), same(this.beanProvider)).will(
				returnValue(component));

		EntityManagerInjector injector = new EntityManagerInjector(wrapped, this.introspector);
		injector.newInstance(logicRequest, this.beanProvider);

		assertSame("injected", component.getEntityManager(), this.entityManager);
	}

	public void testNotInjectEntityManagerIfMissingPersistenceContextAnnotation() throws Exception {
		EntityManagerWrongDependent component = new EntityManagerWrongDependent();
		DefaultLogicRequest logicRequest = createLogicRequest(component);
		logicRequest.setLogicDefinition(new DefaultLogicDefinition(component, createComponentType(EntityManagerWrongDependent.class), null));

		// expects delegation
		Mock constructorMock = mock(BeanConstructor.class);
		BeanConstructor wrapped = (BeanConstructor) constructorMock.proxy();
		constructorMock.expects(once()).method("newInstance").with(same(logicRequest), same(this.beanProvider)).will(
				returnValue(component));

		EntityManagerInjector injector = new EntityManagerInjector(wrapped, this.introspector);
		injector.newInstance(logicRequest, this.beanProvider);

		assertNull("not injected", component.getEntityManager());
	}

	public void testIgnoresNonDependentComponentCreation() throws Exception {
		EntityManagerIndependent component = new EntityManagerIndependent();
		DefaultLogicRequest logicRequest = createLogicRequest(component);
		logicRequest.setLogicDefinition(new DefaultLogicDefinition(component, createComponentType(EntityManagerIndependent.class), null));

		// expects delegation
		Mock constructorMock = mock(BeanConstructor.class);
		BeanConstructor wrapped = (BeanConstructor) constructorMock.proxy();
		constructorMock.expects(once()).method("newInstance").with(same(logicRequest), same(this.beanProvider)).will(
				returnValue(component));

		EntityManagerInjector injector = new EntityManagerInjector(wrapped, this.introspector);
		injector.newInstance(logicRequest, this.beanProvider);

		// ok, no errors
	}

	private DefaultLogicRequest createLogicRequest(Object component) throws InvalidComponentException {
		DefaultLogicMethod logicMethod = createLogicMethod(component.getClass(), "logic");
		DefaultLogicRequest logicRequest = createLogicRequest("dependent", "logic");
		logicRequest.setLogicDefinition(new DefaultLogicDefinition(component, createComponentType(EntityManagerDependent.class), logicMethod));
		// assume there is already an EntityManager in LogicRequest,
		// created by the JavaPersistenceInterceptor
		logicRequest.getRequestContext().setAttribute(EntityManager.class.getName(), this.entityManager);

		return logicRequest;
	}

}
