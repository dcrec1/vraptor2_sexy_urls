package org.vraptor.plugin.hibernate;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.hibernate.validator.Valid;
import org.vraptor.LogicRequest;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.MethodParameter;
import org.vraptor.i18n.FixedMessage;
import org.vraptor.plugin.LogicMethodWrapper;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.validator.UnstableValidationException;
import org.vraptor.validator.ValidationErrors;

/**
 * Wrapper for a method which validates itself using hibernate validator
 * annotations.
 *
 * @author Guilherme Silveira
 *
 */
class HibernateLogicMethod extends LogicMethodWrapper {

	private static final Logger LOG = Logger.getLogger(HibernateLogicMethod.class);

	private final Validate validate;

	private final ValidatorLocator locator;

	private final List<MethodParameter> params;

	public HibernateLogicMethod(Class containingType, LogicMethod method, ValidatorLocator locator) {
		super(method);
		this.validate = method.getMetadata().getAnnotation(Validate.class);
		this.params = method.getParameters();
		this.locator = locator;
	}

	@Override
	public ValidationErrors validate(Object component, LogicRequest context, ResourceBundle bundle, Object[] params)
			throws UnstableValidationException {
		ValidationErrors errors = super.validate(component, context, bundle, params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("Loading hibernate validation for type " + component.getClass().getName());
		}
		if (bundle != null) {
			bundle = ResourceBundle.getBundle("org.hibernate.validator.resources.DefaultValidatorMessages", bundle
					.getLocale());
		}
		for (String field : validate.fields()) {
			try {
				validateField(component, context, bundle, errors, field);
			} catch (GettingException e) {
				throw new UnstableValidationException("Unable to validate objects", e);
			}
		}
		for (String field : validate.params()) {
			try {
				validateParam(component, context, bundle, errors, field, params);
			} catch (GettingException e) {
				throw new UnstableValidationException("Unable to validate objects", e);
			}
		}
		return errors;
	}

	private void validateField(Object component, LogicRequest context, ResourceBundle bundle, ValidationErrors errors,
			String field) throws GettingException {

		String[] values = field.split("\\.");
		Object current = component;

		for (int i = 0; i < values.length - 1; i++) {
			current = ReflectionUtil.get(current, ReflectionUtil.getField(component.getClass(), values[i]));
		}

		validateObject(context, bundle, errors, values, current);

	}

	private void validateParam(Object component, LogicRequest context, ResourceBundle bundle, ValidationErrors errors,
			String key, Object[] paramsValues) throws GettingException {

		String[] values = key.split("\\.");

		Object current = findParameter(values[0], paramsValues);

		if (values.length == 1) {
			// validate the parameter itself
			ClassValidator<Object> validator = (ClassValidator<Object>) locator.getValidator(current.getClass(), bundle);
			InvalidValue[] invalidValues = validator.getInvalidValues(current);

			for (InvalidValue value : invalidValues) {
				errors.add(new FixedMessage(values[0] + "." + value.getPropertyPath(),
						value.getMessage() ,value.getPropertyName()));
			}

		} else {

			for (int i = 1; i < values.length - 1; i++) {
				current = ReflectionUtil.get(current, ReflectionUtil.getField(component.getClass(), values[i]));
			}

			validateObject(context, bundle, errors, values, current);
		}

		if(errors.size() != 0) {
            context.getRequestContext().setAttribute(values[0], current);
		}
	}

	private void validateObject(LogicRequest context, ResourceBundle bundle, ValidationErrors errors, String[] values,
			Object current) throws GettingException {

		Class<? extends Object> type = current.getClass();
		String fieldName = values[values.length - 1];

		FieldValidation validation;

		if (ReflectionUtil.getField(type, fieldName).isAnnotationPresent(Valid.class)) {
			validation = new RecursiveFieldValidation(type, fieldName, locator);
		} else {
			validation = new EntireFieldValidation(type, fieldName, locator);
		}

		validation.validate(current, context, errors, bundle);

	}

	private Object findParameter(String key, Object[] paramsValues) throws GettingException {
		for (int i = 0; i < params.size(); i++) {
			MethodParameter parameter = params.get(i);
			if (parameter.getKey().equals(key)) {
				return paramsValues[i];
			}
		}
		throw new GettingException("Unable to find parameter " + key + " for validation.", null);
	}

}
