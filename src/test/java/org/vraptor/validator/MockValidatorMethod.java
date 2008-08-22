package org.vraptor.validator;

import org.vraptor.annotations.Component;
import org.vraptor.i18n.Message;

@Component("validatorMethod")
public class MockValidatorMethod {

	public void actionWithValidationListOk() {
	}

	public void validateActionWithValidationListOk(ValidationErrors errors) {
	}

	public void actionWithValidationProblem() {
	}

	public void validateActionWithValidationProblem(ValidationErrors errors) {
		errors
				.add(new Message("starting_date",
						"invalid_date_message"));
	}

}
