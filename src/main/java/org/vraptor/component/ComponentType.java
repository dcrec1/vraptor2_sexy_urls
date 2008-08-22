package org.vraptor.component;

import java.util.Collection;
import java.util.List;

import org.vraptor.annotations.In;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.introspector.ReadParameter;
import org.vraptor.scope.ScopeType;

/**
 * Wrapper for a component type in vraptor.
 * 
 * @author Guilherme Silveira
 */
public interface ComponentType extends Outjectable {

	ScopeType getScope();

	/**
	 * Returns the component name
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Returns a specifig logic from this component
	 * 
	 * @param key
	 *            the logic name
	 * @return the logic itself
	 * @throws LogicNotFoundException
	 *             you are asking for something that doesn't belong to me
	 */
	LogicMethod getLogic(String key) throws LogicNotFoundException;

	/**
	 * Returns all interceptors for this component class
	 * 
	 * @return the interceptor's list
	 */
	List<InterceptorType> getInterceptors();

	/**
	 * @return Returns the inAnnotations.
	 */
	List<FieldAnnotation<In>> getInAnnotations();

	/**
	 * @return Returns the read parameters for this class.
	 */
	List<ReadParameter> getReadParameters();

	/**
	 * Returns all actions.
	 * 
	 * @return all logics
	 * @throws LogicNotFoundException 
	 */
	Collection<LogicMethod> getLogics() throws LogicNotFoundException;

	Class getComponentClass();

	@Deprecated
	String getKey();

	@Deprecated
	String getDestroyLogicName();

	/**
	 * Returns this components contructor.
	 * 
	 * @return the contructor
	 * @since 2.2.4
	 */
	public BeanConstructor getConstructor();

}