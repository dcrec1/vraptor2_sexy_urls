package org.vraptor.plugin.hibernate;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.validator.InvalidValue;
import org.vraptor.LogicRequest;
import org.vraptor.i18n.FixedMessage;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.validator.ValidationErrors;

/**
 * An hibernate recursive validator strategy.
 *
 * @author Guilherme Silveira
 */
class RecursiveFieldValidation implements FieldValidation {

	private static final Logger LOG = Logger.getLogger(RecursiveFieldValidation.class);

	private final Field field;

	private final Class type;

	private final ValidatorLocator locator;

	@SuppressWarnings("unchecked")
	public RecursiveFieldValidation(Class type, String field, ValidatorLocator locator) throws GettingException {
		this.field = ReflectionUtil.getField(type, field);
		this.locator = locator;
		this.type = this.field.getType();
	}

	@SuppressWarnings("unchecked")
	public void validate(Object component, LogicRequest context, ValidationErrors errors, ResourceBundle bundle) {
		try {
			InvalidValue[] invalidValues = locator.getValidator(type, bundle).getInvalidValues(
					ReflectionUtil.get(component, field));
			for (InvalidValue value : invalidValues) {
				FixedMessage msg = new FixedMessage(field.getName() + "." + value.getPropertyPath(), value.getMessage(), value.getPropertyName());
				errors.add(msg);
			}
		} catch (Exception e) {
			errors.add(new FixedMessage(field.getName(), "unable_to_validate_field", field.getName()));
			LOG.error("Unable to validate field " + field.getName(), e);
		}
	}

}
