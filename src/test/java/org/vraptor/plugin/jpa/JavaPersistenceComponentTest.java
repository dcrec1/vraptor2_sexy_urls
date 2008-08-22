package org.vraptor.plugin.jpa;

import java.util.List;

import org.vraptor.AbstractTest;
import org.vraptor.component.BeanConstructor;
import org.vraptor.component.ComponentType;
import org.vraptor.interceptor.InterceptorType;

/**
 * @author Fabio Correia Kung
 *
 */
public class JavaPersistenceComponentTest extends AbstractTest {

	private ComponentType dependentType;

	private EntityManagerIntrospector introspector;

	private ComponentType wrongDependentType;

	private ComponentType independentType;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.dependentType = createComponentType(EntityManagerDependent.class);
		this.wrongDependentType = createComponentType(EntityManagerWrongDependent.class);
		this.independentType = createComponentType(EntityManagerIndependent.class);
		this.introspector = new EntityManagerIntrospector();
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.JavaPersistenceComponent#getInterceptors()}.
	 */
	public void testAddsJavaPersistenceInterceptor() {
		InterceptorType expected = InterceptorType.getType(JavaPersistenceInterceptor.class);
		JavaPersistenceComponent component = new JavaPersistenceComponent(this.dependentType, this.introspector);
		List<InterceptorType> interceptors = component.getInterceptors();
		assertTrue("added", interceptors.contains(expected));
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.JavaPersistenceComponent#getConstructor()}.
	 */
	public void testReturnEntityManagerInjectorForDependentComponent() {
		JavaPersistenceComponent component = new JavaPersistenceComponent(this.dependentType, this.introspector);
		BeanConstructor constructor = component.getConstructor();
		assertTrue("must be an EntityInjector", constructor instanceof EntityManagerInjector);
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.JavaPersistenceComponent#getConstructor()}.
	 */
	public void testNotReturnEntityManagerInjectorForMissingPersistenceContextAnnotation() {
		JavaPersistenceComponent component = new JavaPersistenceComponent(this.wrongDependentType, this.introspector);
		BeanConstructor constructor = component.getConstructor();
		assertFalse("must not be an EntityInjector", constructor instanceof EntityManagerInjector);
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.JavaPersistenceComponent#getConstructor()}.
	 */
	public void testNotReturnEntityManagerInjectorForIndependentComponent() {
		JavaPersistenceComponent component = new JavaPersistenceComponent(this.independentType, this.introspector);
		BeanConstructor constructor = component.getConstructor();
		assertFalse("must not be an EntityInjector", constructor instanceof EntityManagerInjector);

	}
}
