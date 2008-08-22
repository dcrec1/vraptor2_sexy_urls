package org.vraptor.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.vraptor.i18n.ValidationMessage;

/**
 * A basic list implementation of validation errors.
 * 
 * @author Guilherme Silveira
 */
public class BasicValidationErrors implements ValidationErrors {

	private final List<ValidationMessage> errors;

	public BasicValidationErrors() {
		this(new ArrayList<ValidationMessage>());
	}

	public BasicValidationErrors(List<ValidationMessage> errors) {
		this.errors = errors;
	}

	/**
	 * 
	 * @see org.vraptor.validator.ValidationErrors#add(org.vraptor.i18n.Message)
	 */
	public void add(ValidationMessage error) {
		errors.add(error);
	}

	/**
	 * 
	 * @see org.vraptor.validator.ValidationErrors#size()
	 */
	public int size() {
		return errors.size();
	}

	public Iterator<ValidationMessage> getIterator() {
		return errors.iterator();
	}

	public Iterator<ValidationMessage> iterator() {
		return errors.iterator();
	}

	@Override
	public String toString() {
		return "[Errors: " + errors.toString() + "]";
	}

	public boolean isEmptyFor(String path) {
		if (path == null) {
			return errors.isEmpty();
		}
		for (ValidationMessage msg : this.errors) {
			if (msg.getPath().equals(path)) {
				return false;
			}
		}
		return true;
	}

}
