package org.vraptor.plugin.jpa;

import java.lang.reflect.Field;

import org.vraptor.AbstractTest;
import org.vraptor.component.ComponentType;
import org.vraptor.component.DefaultLogicMethod;

/**
 * @author Fabio Correia Kung
 *
 */
public class EntityManagerIntrospectorTest extends AbstractTest {

	private EntityManagerIntrospector introspector;

	private ComponentType wrongDependentType;

	private ComponentType dependentType;

	private ComponentType independentType;

	private ComponentType constructorDependentType;

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.introspector = new EntityManagerIntrospector();
		this.constructorDependentType = createComponentType(DependentByConstructor.class);
		this.dependentType = createComponentType(EntityManagerDependent.class);
		this.wrongDependentType = createComponentType(EntityManagerWrongDependent.class);
		this.independentType = createComponentType(EntityManagerIndependent.class);

	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#getEntityManagerField(org.vraptor.component.ComponentType)}.
	 */
	public void testGetEntityManagerFieldForDependentComponent() throws Exception {
		Field expectedField = EntityManagerDependent.class.getDeclaredField("entityManager");
		Field field = this.introspector.getEntityManagerField(dependentType);
		assertEquals("Same field", expectedField, field);
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#getEntityManagerField(org.vraptor.component.ComponentType)}.
	 */
	public void testGetEntityManagerShouldGiveNullForIndependentComponent() throws Exception {
		Field field = this.introspector.getEntityManagerField(independentType);
		assertNull("hasn't the field", field);
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#getEntityManagerField(org.vraptor.component.ComponentType)}.
	 */
	public void testGetEntityManagerShouldGiveNullForMissingPersistenceContextAnnotation() throws Exception {
		Field field = this.introspector.getEntityManagerField(wrongDependentType);
		assertNull("mising @PersistenceContext", field);
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#dependsOnEntityManager(org.vraptor.component.ComponentType)}.
	 */
	public void testPersistenceContextAnnotatedFieldIsNotConstructorDependency() {
		boolean depends = this.introspector.dependsOnEntityManager(dependentType);
		assertFalse(depends);
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#dependsOnEntityManager(org.vraptor.component.ComponentType)}.
	 */
	public void testIndependentComponentHasNotConstructorDependency() {
		boolean depends = this.introspector.dependsOnEntityManager(independentType);
		assertFalse(depends);
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#dependsOnEntityManager(org.vraptor.component.ComponentType)}.
	 */
	public void testWrongDependentComponentHasNotConstructorDependency() {
		boolean depends = this.introspector.dependsOnEntityManager(wrongDependentType);
		assertFalse(depends);
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#dependsOnEntityManager(org.vraptor.component.ComponentType)}.
	 */
	public void testComponentThatDependsOnEntityManagerByConstructor() {
		boolean depends = this.introspector.dependsOnEntityManager(constructorDependentType);
		assertTrue(depends);
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#hasPersistenceContext(org.vraptor.component.ComponentType)}.
	 */
	public void testEntityManagerDependentComponentHasPersistenceContext() {
		assertTrue(this.introspector.hasPersistenceContext(dependentType));
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#hasPersistenceContext(org.vraptor.component.ComponentType)}.
	 */
	public void testEntityManagerIndependentComponentHasNotPersistenceContext() {
		assertFalse(this.introspector.hasPersistenceContext(independentType));
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#hasPersistenceContext(org.vraptor.component.ComponentType)}.
	 */
	public void testMissingPersistenceContextAnnotationComponentHasNotPersistenceContext() {
		assertFalse(this.introspector.hasPersistenceContext(wrongDependentType));
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#hasPersistenceContext(org.vraptor.component.ComponentType)}.
	 */
	public void testDependentByConstructorComponentHasNotPersistenceContext() {
		assertFalse(this.introspector.hasPersistenceContext(constructorDependentType));
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#getPersistenceUnitName(org.vraptor.component.ComponentType)}.
	 *
	 * @throws Exception
	 */
	public void testGetPersistenceUnitName() throws Exception {
		assertEquals("default", EntityManagerIntrospector.DEFAULT_PERSISTENCE_UNIT);

		String nameForDependent = this.introspector.getPersistenceUnitName(dependentType);
		assertEquals("default name", EntityManagerIntrospector.DEFAULT_PERSISTENCE_UNIT, nameForDependent);

		String nameForIndependent = this.introspector.getPersistenceUnitName(independentType);
		assertEquals("default name", EntityManagerIntrospector.DEFAULT_PERSISTENCE_UNIT, nameForIndependent);

		String nameWithoutAnnotation = this.introspector.getPersistenceUnitName(wrongDependentType);
		assertEquals("default name", EntityManagerIntrospector.DEFAULT_PERSISTENCE_UNIT, nameWithoutAnnotation);

		String customName = this.introspector.getPersistenceUnitName(createComponentType(CustomUnitName.class));
		assertEquals("customized name", "otherName", customName);
	}

	/**
	 * Test method for
	 * {@link org.vraptor.plugin.jpa.EntityManagerIntrospector#isTransactionRequired(org.vraptor.component.LogicMethod)}.
	 *
	 * @throws Exception
	 */
	public void testIsTransactionRequired() throws Exception {
		DefaultLogicMethod requiresTransactionLogic = createLogicMethod(WithTransaction.class, "logic");
		DefaultLogicMethod missingRequiredAttributeLogic = createLogicMethod(WithoutTransaction.class, "logic");
		DefaultLogicMethod notAnnotatedLogic = createLogicMethod(EntityManagerDependent.class, "logic");

		assertTrue(this.introspector.isTransactionRequired(requiresTransactionLogic));
		assertFalse("default value for required is false", this.introspector
				.isTransactionRequired(missingRequiredAttributeLogic));
		assertFalse("default behavior is no transaction", this.introspector.isTransactionRequired(notAnnotatedLogic));
	}

}
