package org.vraptor.plugin.hibernate;

import junit.framework.TestCase;

import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;
import org.vraptor.i18n.ValidationMessage;
import org.vraptor.reflection.GettingException;
import org.vraptor.validator.BasicValidationErrors;

public class RecursiveFieldValidationTest extends TestCase {

	public static class SimpleLogic {
		@SuppressWarnings("unused")
		@Valid
		private User user = new User();
	}
	
	public static class User {
		@NotNull
		@SuppressWarnings("unused")
		private Long id;
	}

	public void testChecksAValidField() throws GettingException {

		SimpleLogic h = new SimpleLogic();
		h.user.id = 1L;

		FieldValidation val = new RecursiveFieldValidation(SimpleLogic.class, "user", new ValidatorLocator());

		BasicValidationErrors errors = new BasicValidationErrors();

		val.validate(h, null, errors, null);

		assertEquals(0, errors.size());

	}

	public void testChecksAnInvalidField() throws GettingException {

		SimpleLogic h = new SimpleLogic();

		FieldValidation val = new RecursiveFieldValidation(SimpleLogic.class, "user", new ValidatorLocator());

		BasicValidationErrors errors = new BasicValidationErrors();

		val.validate(h, null, errors, null);

		assertEquals(1, errors.size());

	}

	public void testChecksAnInvalidFieldPath() throws GettingException {

		SimpleLogic h = new SimpleLogic();
		FieldValidation val = new RecursiveFieldValidation(SimpleLogic.class, "user", new ValidatorLocator());
		BasicValidationErrors errors = new BasicValidationErrors();
		val.validate(h, null, errors, null);
		ValidationMessage message = errors.iterator().next();
		assertEquals("user.id", message.getPath());

	}

}
