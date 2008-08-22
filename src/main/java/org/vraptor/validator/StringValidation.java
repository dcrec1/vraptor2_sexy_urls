package org.vraptor.validator;

/**
 * Utility methods for string validation
 * 
 * @author Guilherme Silveira
 */
public class StringValidation {

	/**
	 * Returns true if the string is null or trimmed empty.
	 * 
	 * @param value
	 *            the string to check
	 * @return true if null or trimmed empty
	 */
	public static boolean isBlank(String value) {
		return value == null || value.trim().equals("");
	}

	/**
	 * Returns true if the string is null or empty.
	 * 
	 * @param value
	 *            the string to check
	 * @return true if null or empty
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.equals("");
	}
}
