package org.vraptor.plugin.hibernate;

import org.apache.log4j.Logger;
import org.vraptor.component.ComponentType;
import org.vraptor.component.ComponentTypeWrapper;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;

/**
 * A wrapper for the hibernate component on validation.
 *
 * @author Guilherme Silveira
 */
class HibernateComponent extends ComponentTypeWrapper {

	private static final Logger LOG = Logger.getLogger(HibernateLogicMethod.class);

	private final ValidatorLocator locator;

	public HibernateComponent(ComponentType component, ValidatorLocator locator) {
		super(component);
		this.locator = locator;
		if (LOG.isDebugEnabled()) {
			LOG.debug("New hibernate component created wrapping " + component);
		}
	}

	@Override
	public LogicMethod getLogic(String key) throws LogicNotFoundException {
		LogicMethod logicMethod = super.getLogic(key);
		if (logicMethod == null || !logicMethod.getMetadata().isAnnotationPresent(Validate.class)) {
			return logicMethod;
		} else {
			return new HibernateLogicMethod(getComponentClass(), logicMethod, locator);
		}
	}

}
