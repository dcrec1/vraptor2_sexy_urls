package org.vraptor.plugin.hibernate;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.vraptor.LogicRequest;
import org.vraptor.i18n.FixedMessage;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.validator.ValidationErrors;

class EntireFieldValidation implements FieldValidation {

	private static final Logger logger = Logger.getLogger(EntireFieldValidation.class);

	private final ValidatorLocator locator;

	private final Field field;

	private final String fieldName;

	private final Class type;

	@SuppressWarnings("unchecked")
	public EntireFieldValidation(Class type, String field, ValidatorLocator locator) throws GettingException {
		this.field = ReflectionUtil.getField(type, field);
		this.fieldName = field;
		this.locator = locator;
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public void validate(Object component, LogicRequest context, ValidationErrors errors, ResourceBundle bundle) {
		try {
			ClassValidator validator = locator.getValidator(type, bundle);
			InvalidValue[] invalidValues = validator.getInvalidValues(component, fieldName);
			for (InvalidValue value : invalidValues) {
				FixedMessage msg = new FixedMessage(value.getPropertyPath(), value.getMessage(), value.getPropertyName());
				errors.add(msg);
			}
		} catch (Exception e) {
			errors.add(new FixedMessage(field.getName(), "unable_to_validate_field", field.getName()));
			logger.error("Unable to validate field " + field.getName(), e);
		}
	}

}
