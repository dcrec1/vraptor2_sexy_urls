package org.vraptor.plugin;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

import org.vraptor.LogicException;
import org.vraptor.LogicRequest;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.MethodParameter;
import org.vraptor.validator.UnstableValidationException;
import org.vraptor.validator.ValidationErrors;

public class LogicMethodWrapper implements LogicMethod {

	private final LogicMethod method;

	protected LogicMethodWrapper(LogicMethod logic) {
		this.method = logic;
	}

	public String getName() {
		return method.getName();
	}

	public String execute(Object component, LogicRequest context, Object[] params)
			throws LogicException {
		return method.execute(component, context, params);
	}

	public boolean shouldRedirect() {
		return method.shouldRedirect();
	}

	public ValidationErrors validate(Object component, LogicRequest context,
			ResourceBundle bundle, Object[] params) throws UnstableValidationException {
		return method.validate(component, context, bundle, params);
	}

	public Method getMetadata() {
		return method.getMetadata();
	}

	protected LogicMethod getWrappedMethod() {
		return method;
	}

	public List<MethodParameter> getParameters() {
		return method.getParameters();
	}

	public ComponentType getComponentType() {
		return method.getComponentType();
	}
}
