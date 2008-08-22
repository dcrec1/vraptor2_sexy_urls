package org.vraptor.component;

import java.util.Set;

/**
 * Component managers are capable of finding and dealing with business
 * components/logics.
 * 
 * @author Guilherme Silveira
 */
public interface ComponentManager {

	/**
	 * Registers a new component
	 * 
	 * @param type
	 *            component class
	 * @throws InvalidComponentException
	 *             invalid component
	 */
	boolean register(String type) throws InvalidComponentException;

	/**
	 * Returns the business component
	 * 
	 * @throws LogicNotFoundException
	 * 
	 */
	ComponentType getComponent(String name,String logic) throws ComponentNotFoundException, LogicNotFoundException;

	/**
	 * Returns a list with all component classes.
	 * 
	 * @return all components registered
	 */
	Set<ComponentType> getComponents();

	/**
	 * Directly registers a component type.
	 * 
	 * @param type
	 *            the component type
	 * @throws LogicNotFoundException
	 */
	void register(ComponentType type) throws LogicNotFoundException;

	ComponentType getComponentType(Class<?> type) throws InvalidComponentException;
	
}
