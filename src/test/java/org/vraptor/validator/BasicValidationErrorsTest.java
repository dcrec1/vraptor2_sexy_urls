package org.vraptor.validator;

import junit.framework.TestCase;

import org.vraptor.i18n.FixedMessage;
import org.vraptor.i18n.Message;

public class BasicValidationErrorsTest extends TestCase {

	public void testAllowsNullPathWhileCheckingForEmptyErrors() {
		BasicValidationErrors errors = new BasicValidationErrors();
		assertTrue(errors.isEmptyFor(null));
		errors.add(new FixedMessage("","",""));
		assertFalse(errors.isEmptyFor(null));
	}

	public void testChecksForEmptyErrorsUsingAnSpecificPath() {
		BasicValidationErrors errors = new BasicValidationErrors();
		assertTrue(errors.isEmptyFor("myPath"));
		errors.add(new Message("myPath",""));
		assertFalse(errors.isEmptyFor("myPath"));
	}

}
