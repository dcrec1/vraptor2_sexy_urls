package org.vraptor.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.vraptor.component.ComponentType;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.webapp.WebApplication;

/**
 * Outjects components and plugins variables.
 * 
 * @author Guilherme Silveira
 */
public class StatsQuery {

	/**
	 * Compares the name of two ComponentType, ignoring case. Note: this
	 * comparator imposes orderings that are inconsistent with equals
	 */
	class ComponentTypeNameComparator implements Comparator<ComponentType> {

		public int compare(ComponentType c1, ComponentType c2) {
			return c1.getName().compareToIgnoreCase(c2.getName());
		}
	}

	private List<ComponentType> components;

	private List<VRaptorPlugin> plugins;

	private final WebApplication webApplication;

	public StatsQuery(WebApplication webApplication) {
		this.webApplication = webApplication;
	}

	public List<ComponentType> getComponents() {
		return components;
	}

	public List<VRaptorPlugin> getPlugins() {
		return plugins;
	}

	public void read() {

		this.components = new ArrayList<ComponentType>(this.webApplication.getComponentManager().getComponents());
		Collections.sort(this.components, new ComponentTypeNameComparator());

		this.plugins = this.webApplication.getPluginManager().getPlugins();
	}
}
