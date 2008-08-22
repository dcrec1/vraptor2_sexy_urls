package org.vraptor.config;

import java.util.List;

import org.vraptor.webapp.WebApplication;

/**
 * Basic vraptor configuration pojo.
 *
 * @author Guilherme Silveira
 * @since 2.3.2
 */
public class VRaptorConfiguration {

	private boolean autoDiscover = true;

	private List<ConfigItem> items;

	public boolean isAutoDiscover() {
		return autoDiscover;
	}

	public List<ConfigItem> getItems() {
		return items;
	}

	/**
	 * Reads all components.
	 *
	 * @throws ConfigException
	 */
	public void readComponents(WebApplication application) throws ConfigException {
		if (items != null) {
			for (ConfigItem item : items) {
				if (item.isComponent()) {
					item.register(application);
				}
			}
		}
	}

	/**
	 * Reads all managers.
	 *
	 * @throws ConfigException
	 */
	public void readManagers(WebApplication application) throws ConfigException {
		if (items != null) {
			for (ConfigItem item : items) {
				if (item.isManager()) {
					item.register(application);
				}
			}
		}
	}

}
