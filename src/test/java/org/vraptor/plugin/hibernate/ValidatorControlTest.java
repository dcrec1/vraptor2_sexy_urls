package org.vraptor.plugin.hibernate;

import org.vraptor.AbstractTest;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicNotFoundException;

public class ValidatorControlTest extends AbstractTest {

	@SuppressWarnings("unused")
	public static class DoesntUseValidator {
		private int field1;

		private int field2;

		public void doIt() {

		}
	}

	@SuppressWarnings("unused")
	public static class UsesValidator {
		private int field1;

		private int field2;

		@Validate(fields = "field1")
		public void doIt() {

		}
	}

	public void testRegistersTypeWhichContainsMethodUsingValidateAnnotation() throws InvalidComponentException, LogicNotFoundException {
		ValidatorControl control = new ValidatorControl();
		boolean should = control.needsToWrap(createComponentType(UsesValidator.class));
		assertTrue(should);
	}

	public void testRegistersTypeWhichDoesNotUseThePlugin() throws InvalidComponentException, LogicNotFoundException {
		ValidatorControl control = new ValidatorControl();
		boolean should = control.needsToWrap(createComponentType(DoesntUseValidator.class));
		assertFalse(should);
	}

}
