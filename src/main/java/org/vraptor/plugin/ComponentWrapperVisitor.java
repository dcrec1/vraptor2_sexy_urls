package org.vraptor.plugin;

import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicNotFoundException;

/**
 * A visitor to every component which checks the component wrapper to decide
 * whether they should be wrapped or not.
 *
 * @author Guilherme Silveira
 * @since 2.4
 */
public class ComponentWrapperVisitor {

	private final ComponentWrapper wrapper;

	public ComponentWrapperVisitor(ComponentWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public void visit(ComponentManager manager) throws LogicNotFoundException {
		for (ComponentType component : manager.getComponents()) {
			if (wrapper.needsToWrap(component)) {
				manager.register(wrapper.wrap(component));
			}
		}
	}

}
