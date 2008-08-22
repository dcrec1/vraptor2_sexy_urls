package org.vraptor.config;

import org.vraptor.component.InvalidComponentException;
import org.vraptor.webapp.WebApplication;

/**
 * Models the xml portion to deal with component classes.
 * 
 * @author Guilherme Silveira
 */
public class ComponentConfig implements ConfigItem {

	private final String typeName;

	public ComponentConfig(String typeName) {
		this.typeName = typeName;
	}

	public void register(WebApplication application) throws ConfigException {
		try {
			application.getComponentManager().register(this.typeName);
		} catch (InvalidComponentException e) {
			throw new ConfigException(e);
		}
	}

	public boolean isComponent() {
		return true;
	}

	public boolean isManager() {
		return false;
	}

	public String getTypeName() {
		return typeName;
	}

}
