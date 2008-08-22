package org.vraptor;

import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;

/**
 * The logic definition to be executed.
 *
 * @author Guilherme Silveira
 * @since 2.4
 */
public interface LogicDefinition {

	/**
	 * Returns the component where the action will be executed.
	 * @return	the component
	 */
	Object getComponent();

	ComponentType getComponentType();

	LogicMethod getLogicMethod();

	Object[] getParameters();

}
