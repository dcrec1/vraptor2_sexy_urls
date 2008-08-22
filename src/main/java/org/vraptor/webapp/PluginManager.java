package org.vraptor.webapp;

import java.util.List;

import org.vraptor.VRaptorException;
import org.vraptor.plugin.VRaptorPlugin;

/**
 * A simple plugin manager interface.
 * 
 * @author Guilherme Silveira
 */
public interface PluginManager {

	void register(VRaptorPlugin plugin);

	List<VRaptorPlugin> getPlugins();

	void init(WebApplication application) throws VRaptorException;

}
