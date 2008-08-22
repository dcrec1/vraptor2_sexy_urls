package org.vraptor.plugin.hibernate;

import org.apache.log4j.Logger;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.plugin.ComponentWrapperVisitor;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.webapp.WebApplication;

/**
 * Overrides all components implementing hibernate validation before executing a
 * business logic.
 *
 * @author Guilherme Silveira
 */
public class HibernateValidatorPlugin implements VRaptorPlugin {

	private static final Logger LOG = Logger.getLogger(HibernateValidatorPlugin.class);

	private final ValidatorControl control = new ValidatorControl();

	public void init(WebApplication application) throws LogicNotFoundException {
		LOG.info("Initializing HibernateValidatorPlugin");
		ComponentWrapperVisitor visitor = new ComponentWrapperVisitor(control);
		visitor.visit(application.getComponentManager());
	}

}
