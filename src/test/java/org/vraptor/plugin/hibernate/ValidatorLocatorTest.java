package org.vraptor.plugin.hibernate;

import java.util.ResourceBundle;

import junit.framework.TestCase;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.NotNull;

public class ValidatorLocatorTest extends TestCase {

	public void testCreatesNewValidatorWithItsNullBundle() {
		ValidatorLocator loc = new ValidatorLocator();
		ClassValidator<WithValidations> validator = loc.getValidator(WithValidations.class);
		assertTrue(validator.hasValidationRules());
	}

	public void testCreatesNewValidator() {
		ValidatorLocator loc = new ValidatorLocator();
		ClassValidator<WithValidations> validator = loc.getValidator(WithValidations.class, ResourceBundle
				.getBundle("messages"));
		assertTrue(validator.hasValidationRules());
	}

	public void testGrabsTheCachedValidator() {
		ValidatorLocator loc = new ValidatorLocator();
		ClassValidator<ValidatorLocatorTest> validator = loc.getValidator(ValidatorLocatorTest.class, ResourceBundle
				.getBundle("messages"));
		ClassValidator<ValidatorLocatorTest> validator2 = loc.getValidator(ValidatorLocatorTest.class, ResourceBundle
				.getBundle("messages"));
		assertEquals(validator, validator2);
	}

}

class WithValidations {
	@SuppressWarnings("unused")
	@NotNull
	private int i;
}
