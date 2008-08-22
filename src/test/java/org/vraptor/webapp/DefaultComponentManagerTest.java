package org.vraptor.webapp;

import java.util.List;

import org.vraptor.AbstractTest;
import org.vraptor.annotations.Component;
import org.vraptor.annotations.Destroy;
import org.vraptor.annotations.Parameter;
import org.vraptor.annotations.Read;
import org.vraptor.component.ComponentContainer;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.DefaultComponentContainer;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.introspector.ReadParameter;
import org.vraptor.scope.ScopeType;

public class DefaultComponentManagerTest extends AbstractTest {
	
	private ComponentManager manager;

	protected void setUp() throws Exception {
		super.setUp();
		ComponentContainer container = new DefaultComponentContainer();
		this.manager = registry.getComponentManager();
	}

	public static class ComponentTest {
		public void method1(int i) {

		}

		public void method2(int i) {

		}

		void method3(int i) {
		}
	}

	public static class ComponentTest2 {
		public void method1(int i) {

		}
	}

	private class PrivateClass {

	}

	@Component("SameName")
	public static class SameName1 {
		public void method1(int i) {

		}
	}

	@Component("SameName")
	public static class SameName2 {
		public void method2(int i) {

		}
	}

	public void testClassesResolvingToSameComponentName() throws InvalidComponentException, ComponentNotFoundException,
			LogicNotFoundException {
		manager.register(SameName1.class.getName());
		manager.register(SameName2.class.getName());
		manager.getComponent("SameName", "method1");
		manager.getComponent("SameName", "method2");

		try {
			manager.getComponent("SameName1", "method1");
			fail();
		} catch (ComponentNotFoundException e) {

		}
		try {
			manager.getComponent("SameName2", "method1");
			fail();
		} catch (ComponentNotFoundException e) {

		}
	}

	public void testGetComponent() throws InvalidComponentException, ComponentNotFoundException, LogicNotFoundException {
		manager.register(ComponentTest.class.getName());
		manager.register(ComponentTest2.class.getName());

		manager.getComponent("ComponentTest", "method1");
		manager.getComponent("ComponentTest", "method2");
		manager.getComponent("ComponentTest2", "method1");

		try {
			manager.getComponent("ComponentTest", "method3");
			fail();
		} catch (ComponentNotFoundException e) {
			fail();
		} catch (LogicNotFoundException e) {
		}
		try {
			manager.getComponent("other", "method3");
			fail();
		} catch (ComponentNotFoundException e) {

		} catch (LogicNotFoundException e) {
			fail();
		}
	}

	public void testIgnoresPrivateClass() throws InvalidComponentException {
		boolean result = manager.register(PrivateClass.class.getName());
		assertFalse(result);
	}

	public void testBarfesIfClassIsNotFound() {
		try {
			manager.register("asdf");
			fail("Should have barfed");
		} catch (InvalidComponentException e) {
			// ok
		}
	}

	public void testTriesToGetAnInvalidComponent() {
		try {
			manager.getComponent("a", "b");
			fail("Should not find this component");
		} catch (ComponentNotFoundException e) {
		} catch (LogicNotFoundException e) {
			fail();
		}
	}

	public static class TwoConstructors {
		public TwoConstructors() {

		}

		public TwoConstructors(int i) {

		}
	}

	public void testTwoPublicConstructorsAreRejected() {
		try {
			manager.getComponentType(TwoConstructors.class);
			fail();
		} catch (InvalidComponentException e) {
			// ok
		}
	}

	public static class Nameless {

	}

	@Component("overriden")
	public static class Overriden {

	}

	@Component
	public static class Default {

	}

	public void testUsesTheDefaultComponentNameWhenWithoutAnnotation() throws InvalidComponentException {
		String name = this.manager.getComponentType(Nameless.class).getName();
		assertEquals(Nameless.class.getSimpleName(), name);
	}

	public void testUsesAnOverridenName() throws InvalidComponentException {
		String name = this.manager.getComponentType(Overriden.class).getName();
		assertEquals("overriden", name);
	}

	public void testUsesTheDefaultName() throws InvalidComponentException {
		String name = this.manager.getComponentType(Default.class).getName();
		assertEquals(Default.class.getSimpleName(), name);
	}

	@Component
	public static class SomeKindOfLogic {
	}

	public void testUsesTheSpecialNameWithoutNameValueAtAnnotation() throws InvalidComponentException {
		String name = this.manager.getComponentType(SomeKindOfLogic.class).getName();
		assertEquals("somekindof", name);
	}


	public static class DefaultScope {

	}

	@Component
	public static class DefaultOverridenScope {

	}

	public void testUsesTheDefaultOverridenScope() throws InvalidComponentException {
		ScopeType scope = this.manager.getComponentType(DefaultOverridenScope.class).getScope();
		assertEquals(ScopeType.REQUEST, scope);
	}

	@Component(scope = ScopeType.SESSION)
	public static class OverridenScope {

	}

	public void testOverridesTheScope() throws InvalidComponentException {
		ScopeType scope = this.manager.getComponentType(OverridenScope.class).getScope();
		assertEquals(ScopeType.SESSION, scope);
	}

	public static class DefaultDestroy {

	}

	public void testUsesTheDefaultDestroyMethod() throws InvalidComponentException {
		String name = this.manager.getComponentType(DefaultDestroy.class).getDestroyLogicName();
		assertEquals("destroy", name);
	}

	public static class CustomDestroy {

		@Destroy
		public void customized() {

		}

	}

	public void testOverridesTheDestroyMethod() throws InvalidComponentException {
		String name = this.manager.getComponentType(CustomDestroy.class).getDestroyLogicName();
		assertEquals("customized", name);
	}

	@SuppressWarnings("unused")
	public static class Cavalry {
		@Read
		private int units;

		@Parameter
		private String name;

		private String none;
	}

	public void testFindParametersFindsReadAnnotation() throws InvalidComponentException {
		List<ReadParameter> params = manager.getComponentType(Cavalry.class).getReadParameters();
		for (ReadParameter parameter : params) {
			if (parameter.getKey().equals("units")) {
				// ok
				return;
			}
		}
		fail("Did not find read annotation");
	}

	public void testFindParametersFindsParameterAnnotation() throws InvalidComponentException {
		List<ReadParameter> params = manager.getComponentType(Cavalry.class).getReadParameters();
		for (ReadParameter parameter : params) {
			if (parameter.getKey().equals("name")) {
				// ok
				return;
			}
		}
		fail("Did not find parameter annotation");
	}

	public void testFindParametersIgnoresOtherFields() throws InvalidComponentException {
		List<ReadParameter> params = manager.getComponentType(Cavalry.class).getReadParameters();
		for (ReadParameter parameter : params) {
			if (parameter.getKey().equals("none")) {
				fail("Should not include non annotated fields");
			}
		}
		// ok
	}

	public class ClassWithLogics {

		public void logic1() {
		}

		public String logic2() {
			return null;
		}

		protected void nonPublic() {
		}

		public String getName() {
			return null;
		}

	}

	public void testExtractsLogicMethods() throws InvalidComponentException {
		ComponentType type = manager.getComponentType(ClassWithLogics.class);

		try {
			type.getLogic("logic1");
			type.getLogic("logic2");
		} catch (LogicNotFoundException e) {
			fail();
		}

	}

	public void testTriesToFindNonPublicLogic() throws InvalidComponentException {
		ComponentType type = manager.getComponentType(ClassWithLogics.class);
		try {
			type.getLogic("nonPublic");
			fail("Should ignore non public methods");
		} catch (LogicNotFoundException e) {
			// ok
		}
	}

	public void testTriesToFindGetterLogic() throws InvalidComponentException {
		ComponentType type = manager.getComponentType(ClassWithLogics.class);
		try {
			type.getLogic("getName");
			fail("Should not have found it");
		} catch (LogicNotFoundException e) {
			// ok
		}
	}

	public static class ClassWithStaticIgnoredLogic {

		public static void logic1() {
		}

	}

	public void testExtractsIgnoredStaticLogic() throws InvalidComponentException {
		ComponentType type = manager.getComponentType(ClassWithStaticIgnoredLogic.class);

		try {
			type.getLogic("logic1");
			fail("Should have ignored static method");
		} catch (LogicNotFoundException e) {
			// ok
		}
	}

}
