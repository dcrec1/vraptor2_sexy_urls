package org.vraptor.component;

import java.lang.reflect.Type;

import org.vraptor.converter.Converter;
import org.vraptor.introspector.ReadParameter;
import org.vraptor.reflection.SettingException;

/**
 * Defines an argument that a logic method receives.
 *
 * @author Guilherme Silveira
 * @since 2.3
 */
public class MethodParameter implements ReadParameter {

	private final Clazz paramType;

	private final String key;

	private final int position;

	private final Type generic;

	public MethodParameter(Class<?> param, Type generic, int position, String key) {
		this.paramType = new Clazz(param);
		this.position = position;
		this.generic = generic;
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public Class<? extends Converter> getOverridenConverter() {
		return null;
	}

	public Object guaranteeExistence(Object component, Object methodParams[]) throws SettingException {
		return methodParams[position];
	}

	public boolean mightCreate() {
		return true;
	}

	public Class<?> getType() {
		return paramType.getType();
	}

	public void set(Object component, Object[] methodParams, Object value) throws SettingException {
		methodParams[position] = value;
	}

	public Type getGenericType() {
		return generic;
	}

	public Object newInstance() throws ComponentInstantiationException {
		return paramType.newInstance();
	}

	public int getPosition() {
		return this.position;
	}

}
