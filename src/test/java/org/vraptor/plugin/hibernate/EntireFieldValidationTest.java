package org.vraptor.plugin.hibernate;

import junit.framework.TestCase;

import org.hibernate.validator.NotNull;
import org.vraptor.i18n.ValidationMessage;
import org.vraptor.reflection.GettingException;
import org.vraptor.validator.BasicValidationErrors;

public class EntireFieldValidationTest extends TestCase {

	public static class SimpleLogic {
		@SuppressWarnings("unused")
		@NotNull
		private Long id;
	}

	public void testChecksAValidField() throws GettingException {

		SimpleLogic h = new SimpleLogic();
		h.id = 1L;

		FieldValidation val = new EntireFieldValidation(SimpleLogic.class, "id", new ValidatorLocator());

		BasicValidationErrors errors = new BasicValidationErrors();

		val.validate(h, null, errors, null);

		assertEquals(0, errors.size());

	}

	public void testChecksAnInvalidField() throws GettingException {

		SimpleLogic h = new SimpleLogic();

		FieldValidation val = new EntireFieldValidation(SimpleLogic.class, "id", new ValidatorLocator());

		BasicValidationErrors errors = new BasicValidationErrors();

		val.validate(h, null, errors, null);

		assertEquals(1, errors.size());

	}

	public void testChecksAnInvalidFieldPath() throws GettingException {

		SimpleLogic h = new SimpleLogic();
		FieldValidation val = new EntireFieldValidation(SimpleLogic.class, "id", new ValidatorLocator());
		BasicValidationErrors errors = new BasicValidationErrors();
		val.validate(h, null, errors, null);
		ValidationMessage message = errors.iterator().next();
		assertEquals("id", message.getPath());

	}

}
