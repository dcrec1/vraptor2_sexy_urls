package org.vraptor.component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vraptor.ValidationErrorsFactory;
import org.vraptor.annotations.Logic;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.validator.ValidationErrors;
import org.vraptor.webapp.DefaultComponentManager;

/**
 * Factory for logic methods.
 * 
 * @author Guilherme Silveira
 */
public class DefaultLogicMethodFactory implements LogicMethodFactory {

	private static final Logger LOG = Logger.getLogger(DefaultLogicMethodFactory.class);
	private final ValidationErrorsFactory validationFactory;
	private final ParameterInfoProvider paramInfo;

	public DefaultLogicMethodFactory(ValidationErrorsFactory validationFactory, ParameterInfoProvider paramInfoProvider) {
		this.validationFactory = validationFactory;
		this.paramInfo = paramInfoProvider;
	}

	public Map<String, DefaultLogicMethod> loadLogics(Class<?> type) throws InvalidComponentException {
		Map<String, DefaultLogicMethod> actions = new HashMap<String, DefaultLogicMethod>();
		for (Method m : type.getMethods()) {
			if (isNotLogicMethod(m)) {
				LOG.debug("ignoring method " + m + " as logic!");
				continue;
			}
			Logic annotation = m.getAnnotation(Logic.class);
			generateLogics(actions, m, annotation, type);
		}
		return actions;
	}

	private boolean isNotLogicMethod(Method m) {
		return !Modifier.isPublic(m.getModifiers()) || Modifier.isStatic(m.getModifiers())
				|| m.getDeclaringClass().equals(Object.class)
				|| m.getName().startsWith(DefaultComponentManager.VALIDATE_METHOD_INITIALS) || ReflectionUtil.isGetter(m);
	}

	private void generateLogics(Map<String, DefaultLogicMethod> actions,
			Method method, Logic annotation, Class<?> type) throws InvalidComponentException {
		if (annotation == null || annotation.value().length == 0) {
			register(actions, create(method.getName(), type, method));
		} else {
			for (String name : annotation.value()) {
				register(actions, create(name, type, method));
			}
		}
	}

	private void register(Map<String, DefaultLogicMethod> actions, DefaultLogicMethod action) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Registering logic " + action.getName());
		}
		actions.put(action.getName(), action);
	}

	public DefaultLogicMethod create(String name, Class<?> type, Method method) throws InvalidComponentException {
		if (name.indexOf('.') != -1) {
			throw new InvalidComponentException("Type " + type.getName() + " method " + method.getName()
					+ " contains invalid logic with name containing a dot.");
		}
		Method validateMethod = ReflectionUtil.getPrefixedMethod(type, "validate", name, addValidationErrorsToArray(method));
		List<MethodParameter> parameters = paramInfo.provideFor(method);
		return new DefaultLogicMethod(validationFactory, name, method, validateMethod, parameters);
	}

	private Class[] addValidationErrorsToArray(Method method) {

		Class[] types = new Class[method.getParameterTypes().length + 1];
		types[0] = ValidationErrors.class;
		int i = 1;
		for (Class type : method.getParameterTypes()) {
			types[i++] = type;
		}
		return types;
	}
}
