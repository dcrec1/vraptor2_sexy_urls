package org.vraptor.plugin.hibernate;

import java.util.Collection;

import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.plugin.ComponentWrapper;

/**
 * Deals with hibernate validator component registering.
 *
 * @author Guilherme Silveira
 */
class ValidatorControl implements ComponentWrapper {

	private final ValidatorLocator locator;

	public ValidatorControl() {
		locator = new ValidatorLocator();
	}

	public boolean needsToWrap(ComponentType component) throws LogicNotFoundException {
		Collection<LogicMethod> logics = component.getLogics();
		for (LogicMethod logic : logics) {
			if (logic.getMetadata().isAnnotationPresent(Validate.class)) {
				return true;
			}
		}
		return false;
	}

	public ComponentType wrap(ComponentType component) {
		return new HibernateComponent(component, locator);
	}

}
