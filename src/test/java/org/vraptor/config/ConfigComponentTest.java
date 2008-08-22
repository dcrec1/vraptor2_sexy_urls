package org.vraptor.config;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.component.ComponentManager;
import org.vraptor.webapp.WebApplication;

public class ConfigComponentTest extends AbstractTest {

	public void testRegistersTheComponentInAManager() throws ConfigException {
		ComponentConfig cc = new ComponentConfig("mytype");
		Mock mock = mock(WebApplication.class);
		Mock managerMock = mock(ComponentManager.class);
		managerMock.expects(once()).method("register").with(eq("mytype")).will(
				returnValue(true));
		mock.expects(once()).method("getComponentManager").withNoArguments()
				.will(returnValue(managerMock.proxy()));
		cc.register((WebApplication) mock.proxy());
	}

}
