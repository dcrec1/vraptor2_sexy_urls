package org.vraptor.plugin.hibernate;

import java.util.ResourceBundle;

import org.vraptor.LogicRequest;
import org.vraptor.validator.ValidationErrors;

interface FieldValidation {

	void validate(Object component, LogicRequest context,
			ValidationErrors errors, ResourceBundle bundle);

}