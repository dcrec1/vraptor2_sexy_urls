package org.vraptor.plugin;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.VRaptorException;
import org.vraptor.webapp.WebApplication;

public class DefaultPluginManagerTest extends AbstractTest {

	public void testInitAllRegisteredPlugins() throws VRaptorException {
		WebApplication webApp = (WebApplication) mock(WebApplication.class).proxy();
		Mock mock = mock(VRaptorPlugin.class);
		mock.expects(once()).method("init").with(eq(webApp));

		DefaultPluginManager manager = new DefaultPluginManager();
		manager.register((VRaptorPlugin) mock.proxy());
		manager.init(webApp);
	}

}
