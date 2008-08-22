package org.vraptor.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.vraptor.annotations.In;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.introspector.ReadParameter;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.scope.ScopeType;

/**
 * A component wrapper.
 * 
 * @author Guilherme Silveira
 */
public class ComponentTypeWrapper implements ComponentType {

	private final ComponentType type;

	public ComponentTypeWrapper(ComponentType component) {
		this.type = component;
	}

	public List<FieldAnnotation<In>> getInAnnotations() {
		return type.getInAnnotations();
	}

	public List<InterceptorType> getInterceptors() {
		return type.getInterceptors();
	}

	public LogicMethod getLogic(String key) throws LogicNotFoundException {
		return type.getLogic(key);
	}

	public String getName() {
		return type.getName();
	}

	public List<ReadParameter> getReadParameters() {
		return type.getReadParameters();
	}

	public Collection<LogicMethod> getLogics() throws LogicNotFoundException {
		Collection<LogicMethod> original = type.getLogics();
		Collection<LogicMethod> overriden = new ArrayList<LogicMethod>();
		// returns the wrapped logics
		for (LogicMethod method : original) {
			overriden.add(getLogic(method.getName()));
		}
		return overriden;
	}

	public Class getComponentClass() {
		return type.getComponentClass();
	}

	public ScopeType getScope() {
		return type.getScope();
	}

	public String getKey() {
		return type.getKey();
	}

	public String getDestroyLogicName() {
		return type.getDestroyLogicName();
	}

	public Map<String, Object> getOutjectedValues(Object comp, ScopeType scope) throws GettingException, MethodInvocationException {
		return type.getOutjectedValues(comp, scope);
	}

	public BeanConstructor getConstructor() {
		return type.getConstructor();
	}

}
