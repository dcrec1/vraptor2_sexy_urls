package org.vraptor.validator;

import org.vraptor.ValidationErrorsFactory;

public class DefaultValidationErrorsFactory implements ValidationErrorsFactory {

	public BasicValidationErrors newInstance() {
		return new BasicValidationErrors();
	}

}
