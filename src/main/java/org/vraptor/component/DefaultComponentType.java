package org.vraptor.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vraptor.annotations.In;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.introspector.ReadParameter;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.scope.ScopeType;

/**
 * Metadata information about a component class. This class is used to deal with
 * component classes.
 *
 * @author Guilherme Silveira
 */
public class DefaultComponentType implements ComponentType {

	private final Map<String, ? extends LogicMethod> actions;

	private final List<FieldAnnotation<In>> inAnnotations;

	private final List<Outjecter> outjections;

	private final List<ReadParameter> readParameters;

	private final List<InterceptorType> interceptors;

	private final Class<?> clazz;

	private final String name;

	private final BeanConstructor constructor;

	private final ScopeType scope;

	private final String destroyLogicName;

	public <T> DefaultComponentType(Class<T> type, String name, ScopeType scope, BeanConstructor constructor,
			Map<String, ? extends LogicMethod> actions, List<FieldAnnotation<In>> ins, List<InterceptorType> interceptors,
			String destroyLogicName, List<ReadParameter> parameters) {
		this.name = name;
		this.actions = actions;
		this.scope = scope;
		this.inAnnotations = ins;
		this.interceptors = interceptors;
		this.clazz = type;
		this.constructor = constructor;
		this.readParameters = parameters;
		this.destroyLogicName = destroyLogicName;
		this.outjections = ReflectionUtil.loadOutjecters(type);
	}

	public String getName() {
		return this.name;
	}

	public LogicMethod getLogic(String key) throws LogicNotFoundException {
		if (!this.actions.containsKey(key)) {
			throw new LogicNotFoundException("Unable to find logic " + key);
		}
		return this.actions.get(key);
	}

	public List<InterceptorType> getInterceptors() {
		return this.interceptors;
	}

	public List<FieldAnnotation<In>> getInAnnotations() {
		return this.inAnnotations;
	}

	public List<ReadParameter> getReadParameters() {
		return this.readParameters;
	}

	public Collection<LogicMethod> getLogics() {
		return new ArrayList<LogicMethod>(this.actions.values());
	}

	public Class getComponentClass() {
		return this.clazz;
	}

	public ScopeType getScope() {
		return this.scope;
	}

	public String getKey() {
		return "component." + Character.toUpperCase(getComponentClass().getSimpleName().charAt(0))
				+ getComponentClass().getSimpleName().substring(1);
	}

	public String getDestroyLogicName() {
		return this.destroyLogicName;
	}

	public Map<String, Object> getOutjectedValues(Object comp, ScopeType scope) throws GettingException,
			MethodInvocationException {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Outjecter out : outjections) {
			if (out.getScope().equals(scope)) {
				String key = out.getKey();
				Object obj = out.getValue(comp);
				map.put(key, obj);
			}
		}
		return map;
	}

	public BeanConstructor getConstructor() {
		return constructor;
	}

}
