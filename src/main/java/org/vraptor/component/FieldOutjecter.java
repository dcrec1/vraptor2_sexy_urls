package org.vraptor.component;

import java.lang.reflect.Field;

import org.vraptor.annotations.Out;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.scope.ScopeType;

public class FieldOutjecter implements Outjecter {

	private final Field field;

	private final ScopeType scope;

	private final String key;

	public FieldOutjecter(FieldAnnotation<Out> fout) {
		this.field = fout.getField();
		Out out = fout.getAnnotation();
		this.scope = out.scope();
		if (out.key() == null || out.key().equals("")) {
			this.key = field.getName();
		} else {
			this.key = out.key();
		}
	}
	
	public String getKey() {
		return key;
	}

	public ScopeType getScope() {
		return scope;
	}

	public Object getValue(Object obj) throws GettingException, MethodInvocationException {
		return ReflectionUtil.get(obj, field);
	}

}
