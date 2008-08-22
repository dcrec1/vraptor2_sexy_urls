package org.vraptor.plugin;

import java.util.ArrayList;
import java.util.List;

import org.vraptor.VRaptorException;
import org.vraptor.webapp.PluginManager;
import org.vraptor.webapp.WebApplication;

/**
 * The default plugin manager.
 * 
 * @author Guilherme Silveira
 */
public class DefaultPluginManager implements PluginManager {

	private final List<VRaptorPlugin> plugins = new ArrayList<VRaptorPlugin>();

	public void register(VRaptorPlugin pluginClass) {
		plugins.add(pluginClass);
	}

	public List<VRaptorPlugin> getPlugins() {
		return plugins;
	}

	public void init(WebApplication application) throws VRaptorException {
		for (VRaptorPlugin plugin : this.plugins) {
			plugin.init(application);
		}
	}

}
