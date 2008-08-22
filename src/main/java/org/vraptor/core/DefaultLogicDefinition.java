package org.vraptor.core;

import org.vraptor.LogicDefinition;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;

/**
 * The default logic definition implementation.
 *
 * @author Guilherme Silveira
 */
public class DefaultLogicDefinition implements LogicDefinition {

	private final Object component;

	private final ComponentType componentType;

	private final LogicMethod method;

	private Object[] parameters;

	public DefaultLogicDefinition(Object component, ComponentType componentType, LogicMethod method) {
		this.component = component;
		this.componentType = componentType;
		this.method = method;
		this.parameters = new Object[0];
	}

	public Object getComponent() {
		return component;
	}

	public ComponentType getComponentType() {
		return componentType;
	}

	public LogicMethod getLogicMethod() {
		return method;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

}
