package org.vraptor.component;


/**
 * Encapsulates the componentInstantiatedObject type, logic and the instatiated object itself.
 * 
 * @author Paulo Silveira
 * 
 */
public class Component {

	private final ComponentType componentType;

	private final LogicMethod logicMethod;

	public Component(ComponentType componentType, LogicMethod logicMethod) {
		this.componentType = componentType;
		this.logicMethod = logicMethod;
	}

	/**
	 * The componentInstantiatedObject type used to instantiate this componentInstantiatedObject
	 * 
	 * @return
	 */
	public ComponentType getComponentType() {
		return componentType;
	}

	/**
	 * The method that should be invoked in this componentInstantiatedObject
	 * 
	 * @return
	 */
	public LogicMethod getLogicMethod() {
		return logicMethod;
	}

}
