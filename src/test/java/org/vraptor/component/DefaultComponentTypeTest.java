package org.vraptor.component;

import org.vraptor.AbstractTest;

public class DefaultComponentTypeTest extends AbstractTest {

	private ComponentManager manager;
	private ComponentType valid;

	private ComponentType invalid;

	protected void setUp() throws Exception {
		super.setUp();
		manager = registry.getComponentManager();
		invalid = manager.getComponentType(MockInvalidComponent.class);
		valid = manager.getComponentType(MockComponent.class);
	}

	public void testName() {
		assertEquals(valid.getName(), "mock-valid");
		assertEquals(invalid.getName(), "mock-invalid");
	}

	public void testGetLogic() throws LogicNotFoundException {
		assertNotNull(valid.getLogic("simple"));
	}

	public void testInvalidGetLogic() {
		try {
			valid.getLogic("qwerty");
			fail();
		} catch (LogicNotFoundException e) {
			// ok
		}
	}

	public void testDefaultName() throws InvalidComponentException {
		ComponentType defaultComponent = manager
				.getComponentType(MockDefaultComponent.class);
		assertEquals("MockDefaultComponent", defaultComponent.getName());
	}

	public void testReadsReadFields() {
		assertEquals(valid.getReadParameters().size(), 1);
	}

	public void testReadsInFields() {
		assertEquals(valid.getInAnnotations().size(), 2);
	}

	public void testIgnoredObjectMethods() {
		try {
			valid.getLogic("wait");
			fail();
		} catch (LogicNotFoundException e) {
			// ok
		}
	}

	public void testFindsInvalidConstructors() {
		try {
			createComponentType(InvalidConstructors.class);
			fail();
		} catch (InvalidComponentException e) {
			// ok
		}
	}
	public static class InvalidConstructors {
		public InvalidConstructors() {
		}

		public InvalidConstructors(String s) {
		}
	}

}
