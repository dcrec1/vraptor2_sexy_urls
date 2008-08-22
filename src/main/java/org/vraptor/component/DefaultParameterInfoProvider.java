package org.vraptor.component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.vraptor.annotations.Logic;
import org.vraptor.annotations.Parameter;
import org.vraptor.reflection.StringUtil;

/**
 * The default implementation of a parameter info provider. Looks for its type, Logic and Parameter annotation.
 * @author Guilherme Silveira
 * @since 2.5.1
 */
public class DefaultParameterInfoProvider implements ParameterInfoProvider {

	public List<MethodParameter> provideFor(Method method) {
		Class<?>[] params = method.getParameterTypes();
		Type[] generic = method.getGenericParameterTypes();
		Annotation[][] annotations = method.getParameterAnnotations();
		List<MethodParameter> list = new ArrayList<MethodParameter>();

		Logic logic = method.getAnnotation(Logic.class);
		String[] parameterNames = logic != null ? logic.parameters() : null;
		if (parameterNames != null && parameterNames.length != 0
				&& parameterNames.length != method.getParameterTypes().length) {
			throw new IllegalArgumentException(
					"The number of parameters at @Logic does "
							+ "not match the number of this method arguments in method"
							+ method.getName());
		}

		for (int i = 0; i < params.length; i++) {
			Class<?> param = params[i];
			Parameter parameterAnnotation = searchParameterAnnotation(annotations, i);
			String key;

			if (parameterAnnotation != null
					&& !parameterAnnotation.key().equals("")) {
				// using @Parameter key name
				key = parameterAnnotation.key();
			} else {
				// using @Logic(parameters={..} value
				if (parameterNames != null && parameterNames.length != 0) {
					key = parameterNames[i];
				} else {
					key = StringUtil.classNameToInstanceName(param
							.getSimpleName());
				}
			}

			list.add(new MethodParameter(param, generic[i], i, key));
		}
		return list;
	}

	private Parameter searchParameterAnnotation(Annotation[][] annotations, int i) {
		for (Annotation a : annotations[i]) {
			if (a.annotationType().equals(Parameter.class)) {
				return (Parameter) a;
			}
		}
		return null;
	}

}
