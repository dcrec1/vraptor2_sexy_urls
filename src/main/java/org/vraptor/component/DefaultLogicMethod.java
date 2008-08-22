package org.vraptor.component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.vraptor.LogicException;
import org.vraptor.LogicRequest;
import org.vraptor.ValidationErrorsFactory;
import org.vraptor.annotations.Viewless;
import org.vraptor.validator.UnstableValidationException;
import org.vraptor.validator.ValidationErrors;

/**
 * Represents a logic method.
 *
 * @author Guilherme Silveira
 */
public class DefaultLogicMethod implements LogicMethod {

	private static final Logger LOG = Logger.getLogger(DefaultLogicMethod.class);

	private final String name;

	private final Method method;

	private final boolean shouldRedirect;

	private final Method validateMethod;

	private final List<MethodParameter> parameters;

	private final ValidationErrorsFactory validationFactory;

	private ComponentType type;

	/**
	 * Constructs it based on the logics name and method
	 */
	public DefaultLogicMethod(ValidationErrorsFactory factory, String name, Method method, Method validateMethod, List<MethodParameter> parameters) {
		this.validationFactory = factory;
		this.name = name;
		this.method = method;
		this.shouldRedirect = !this.method.isAnnotationPresent(Viewless.class);
		this.validateMethod = validateMethod;
		this.parameters = parameters;
		if (LOG.isDebugEnabled()) {
			LOG.debug("Logic method " + method.getName() + " was read with validation " + this.validateMethod
					+ " and parameters " + parameters);
		}
	}

	public String getName() {
		return this.name;
	}

	public String execute(Object component, LogicRequest context, Object[] params) throws LogicException {
		try {
			Object result = method.invoke(component, params);
			return result == null ? "ok" : result.toString();
		} catch (IllegalArgumentException e) {
			throw new LogicException(e);
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e.getCause());
		}
	}

	public ValidationErrors validate(Object component, LogicRequest context, ResourceBundle bundle, Object[] params)
			throws UnstableValidationException {
		ValidationErrors errors = validationFactory.newInstance();
		if (validateMethod != null) {
			try {
				validateMethod.invoke(component, addValidationErrorsToArray(errors, params));
			} catch (IllegalArgumentException e) {
				LOG.error("Nasty validation method has thrown an exception!", e);
				throw new UnstableValidationException("Error during validation process", e);
			} catch (IllegalAccessException e) {
				LOG.error("Nasty validation method has thrown an exception!", e);
				throw new UnstableValidationException("Error during validation process", e);
			} catch (InvocationTargetException e) {
				LOG.error("Nasty validation method has thrown an exception!", e);
				throw new UnstableValidationException("Error during validation process", e);
			}
		}
		return errors;
	}

	private Object[] addValidationErrorsToArray(ValidationErrors errors, Object[] params) {

		Object[] types = new Object[params.length+1];
		types[0] = errors;

		int i = 1;

		for(Object param : params) {
			types[i++] = param;
		}
		return types;
	}

	public boolean shouldRedirect() {
		return this.shouldRedirect;
	}

	public Method getMetadata() {
		return method;
	}

	public List<MethodParameter> getParameters() {
		return parameters;
	}

	public ComponentType getComponentType() {
		return type;
	}

	public void setComponentType(ComponentType componentType) {
		type = componentType;
	}

}
