package org.vraptor.component;

import java.lang.reflect.Method;

import org.vraptor.annotations.Out;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.scope.ScopeType;

/**
 * A getter outjection.
 * 
 * @author Guilherme Silveira
 * @since 2.2.3
 */
public class GetterOutjecter implements Outjecter {

	private static final Object[] EMPTY_ARRAY = new Object[0];

	private final ScopeType scope;

	private final String key;

	private final Method method;

	public GetterOutjecter(String key, Method method) {
		Out out = method.getAnnotation(Out.class);
		if (out == null) {
			scope = ScopeType.REQUEST;
			this.key = key;
		} else {
			scope = out.scope();
			if (out.key() == null || out.key().equals("")) {
				this.key = key;
			} else {
				this.key = out.key();
			}
		}
		this.method = method;
	}

	public String getKey() {
		return key;
	}

	public ScopeType getScope() {
		return scope;
	}

	public Object getValue(Object obj) throws MethodInvocationException {
		return ReflectionUtil.invoke(obj, method, EMPTY_ARRAY);
	}

}
