package org.vraptor.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.vraptor.LogicException;
import org.vraptor.LogicRequest;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.MethodParameter;
import org.vraptor.validator.UnstableValidationException;
import org.vraptor.validator.ValidationErrors;

public class MockedLogicMethod implements LogicMethod {

	private boolean shouldRedirectAfterExecution;

	public MockedLogicMethod(boolean redirect) {
		this.shouldRedirectAfterExecution = redirect;
	}

	public String getName() {
		return null;
	}

	public String execute(Object component, LogicRequest context, Object[] params) throws LogicException {
		return null;
	}

	public boolean shouldRedirect() {
		return shouldRedirectAfterExecution;
	}

	public ValidationErrors validate(Object component, LogicRequest context, ResourceBundle bundle, Object[] params)
			throws UnstableValidationException {
		return null;
	}

	public Method getMetadata() {
		return null;
	}

	public List<MethodParameter> getParameters() {
		return new ArrayList<MethodParameter>();
	}

	public boolean isRemotable() {
		return false;
	}

	public ComponentType getComponentType() {
		return null;
	}

}
