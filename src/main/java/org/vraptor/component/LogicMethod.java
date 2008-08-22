package org.vraptor.component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

import org.vraptor.LogicException;
import org.vraptor.LogicRequest;
import org.vraptor.validator.UnstableValidationException;
import org.vraptor.validator.ValidationErrors;

/**
 * A business logic method.
 *
 * @author Guilherme Silveira
 */
public interface LogicMethod {

	/**
	 * Returns the logic's name
	 *
	 * @return its name
	 */
	String getName();

	/**
	 * Executes the logic by invoking the method
	 *
	 * @param component
	 *            the component to invoke the method on
	 * @param context
	 * @return the method's result
	 * @throws LogicException
	 *             something wrong happenned
	 */
	String execute(Object component, LogicRequest context, Object[] params) throws LogicException;

	/**
	 * Should execute a redirect after business logic execution.
	 *
	 * @return true if redirection should be done
	 */
	boolean shouldRedirect();

	/**
	 * Validates this logic.
	 *
	 * @param component
	 * @param context
	 * @param methodParamObjects method parameters
	 * @return the errors
	 * @throws UnstableValidationException
	 */
	ValidationErrors validate(Object component, LogicRequest context, ResourceBundle bundle, Object[] methodParamObjects)
			throws UnstableValidationException;

	Method getMetadata();

	List<MethodParameter> getParameters();

	ComponentType getComponentType();

}