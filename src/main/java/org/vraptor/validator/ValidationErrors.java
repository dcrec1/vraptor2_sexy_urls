package org.vraptor.validator;

import java.util.Iterator;

import org.vraptor.i18n.ValidationMessage;

/**
 * Collection of validation errors. List is not used as there should be limited
 * access to some methods.
 * 
 * @author Guilherme Silveira
 */
public interface ValidationErrors extends Iterable<ValidationMessage> {

	/**
	 * Adds a new validation error
	 * 
	 * @param error
	 *            the validation error
	 */
	void add(ValidationMessage error);

	/**
	 * Returns the number of validation errors registered
	 * 
	 * @return the number of validation errors
	 */
	int size();

	/**
	 * Retrieves an iterator
	 * 
	 * @return the iterator
	 */
	Iterator<ValidationMessage> getIterator();

	/**
	 * Checks if this error collection is empty for a specific path.
	 * 
	 * @param path
	 *            the path to check
	 * @return true if its empty for that path.
	 * @since 2.3.2
	 */
	boolean isEmptyFor(String path);

}
