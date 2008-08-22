package org.vraptor.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicDefinition;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.MethodParameter;
import org.vraptor.converter.ConverterManager;
import org.vraptor.core.DefaultLogicDefinition;
import org.vraptor.core.ValidatorEngine;
import org.vraptor.i18n.ValidationMessage;
import org.vraptor.introspector.Introspector;
import org.vraptor.introspector.ReadParameter;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.SettingException;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.validator.ValidationErrors;
import org.vraptor.view.ViewException;
import org.vraptor.view.ViewManager;
import org.vraptor.webapp.WebApplication;

/**
 * Responsible for setters and validation.
 *
 * @author Guilherme Silveira
 * @author Paulo Silveira
 */
public class SettingAndValidationInterceptor implements Interceptor {

	private static final String ERRORS = "errors";

	private static final Logger LOG = Logger.getLogger(SettingAndValidationInterceptor.class);

	private static final String INVALID = "invalid";

	private final ValidatorEngine validatorEngine;

	private final Introspector introspector;

	private ConverterManager converterManager;

	private ViewManager viewManager;

	public SettingAndValidationInterceptor(WebApplication webApp) {
		this.viewManager = webApp.getDefaultViewManager();
		this.validatorEngine = new ValidatorEngine();
		this.introspector = webApp.getIntrospector();
		this.converterManager = webApp.getConverterManager();
	}

	/**
	 * Validates and then Sets the component parameters.
	 *
	 * This interceptor will not continue the logicFlow if an invalid parameter
	 * was passed, this way the ViewInterceptor will not be executed. It will
	 * also confirmResult to INVALID in this case.
	 */
	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		DefaultLogicRequest logicRequest = (DefaultLogicRequest) flow.getLogicRequest();
		LogicDefinition currentDefinition = logicRequest.getLogicDefinition();
		Object component = currentDefinition.getComponent();

		try {

			LogicMethod method = currentDefinition.getLogicMethod();
			List<MethodParameter> methodParams = method.getParameters();
			List<ReadParameter> allParams = new ArrayList<ReadParameter>();
			allParams.addAll(methodParams);
			ComponentType type = currentDefinition.getComponentType();
			allParams.addAll(type.getReadParameters());

			// instantiate parameters
			Object[] methodParamObjects = new Object[methodParams.size()];
			for (int i = 0; i < methodParamObjects.length; i++) {
				try {
					methodParamObjects[i] = methodParams.get(i).newInstance();
				} catch (ComponentInstantiationException e) {
					// do not throw any exception, leave that work for the
					// Converters
					// TODO: check for primitives
					LOG.debug("cant instantiate " + e.getMessage());
					methodParamObjects[i] = null;
				}
			}

			DefaultLogicDefinition logicDefinition = (DefaultLogicDefinition) logicRequest.getLogicDefinition();
			logicDefinition.setParameters(methodParamObjects);

			List<ValidationMessage> problems = introspector.readParameters(allParams, component, logicRequest, converterManager,
					methodParamObjects);

			ValidationErrors errors = validatorEngine.validate(method, logicRequest, introspector, component, methodParamObjects);

			for (ValidationMessage problem : problems) {
				errors.add(problem);
			}

			// TODO what the hell? make it a better design..
			if (errors.size() != 0) {
				logicRequest.getRequestContext().setAttribute(ERRORS, errors);
				if (LOG.isDebugEnabled()) {
					LOG.debug("Some problems were found: " + errors);
				}

				// better design... please
				// outjects no matter its ok or not, so it can refill the form
				// extract null logic flow?
				introspector.outject(logicRequest, component, type);

				redirect(flow);
				return;
			}

		} catch (SettingException e) {
			throw new LogicException(e.getMessage(), e);
		} catch (GettingException e) {
			throw new LogicException(e.getMessage(), e);
		} catch (MethodInvocationException e) {
			throw new LogicException(e);
		}

		flow.execute();
	}

	private void redirect(LogicFlow flow) throws ViewException {
		LogicRequest request = (LogicRequest) flow.getLogicRequest();
		viewManager.forward(request, INVALID);
		flow.getLogicRequest().confirmResult(INVALID);
	}

}
