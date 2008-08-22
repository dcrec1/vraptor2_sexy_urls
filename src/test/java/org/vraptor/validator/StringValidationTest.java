package org.vraptor.validator;

import junit.framework.TestCase;

public class StringValidationTest extends TestCase {

	public void testIsBlankEmptyString() {
		assertTrue(StringValidation.isBlank(""));
	}

	public void testIsBlankTrimmed() {
		assertTrue(StringValidation.isBlank("   \t   "));
	}

	public void testIsBlankNull() {
		assertTrue(StringValidation.isBlank(null));
	}

	public void testIsNotBlank() {
		assertFalse(StringValidation.isBlank("    a    "));
	}

	public void testIsEmptyEmptyString() {
		assertTrue(StringValidation.isEmpty(""));
	}

	public void testIsNotEmptyTrimmed() {
		assertFalse(StringValidation.isEmpty("   \t   "));
	}

	public void testIsEmptyNull() {
		assertTrue(StringValidation.isEmpty(null));
	}

	public void testIsNotEmpty() {
		assertFalse(StringValidation.isEmpty("a"));
	}

}
