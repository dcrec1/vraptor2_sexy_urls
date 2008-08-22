package org.vraptor.config;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Map;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.VRaptorException;
import org.vraptor.plugin.DefaultPluginManager;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.webapp.WebApplication;

public class XMLConfigurationLoaderTest extends AbstractTest {

	public void testLoadsAutoDiscover() {
		String xml = "<vraptor><auto-discover>false</auto-discover></vraptor>";
		VRaptorConfiguration config = getConfig(xml);
		assertFalse(config.isAutoDiscover());
	}

	private VRaptorConfiguration getConfig(String xml) {
		XMLConfigurationLoader loader = new XMLConfigurationLoader();
		VRaptorConfiguration config = loader.read(new StringReader(xml));
		return config;
	}

	public void testDefaultsAutoDiscoverToTrue() {
		String xml = "<vraptor></vraptor>";
		VRaptorConfiguration config = getConfig(xml);
		assertTrue(config.isAutoDiscover());
	}

	public void testLoadsComponents() {
		String xml = "<vraptor><component>" + MyComponent.class.getName() + "</component></vraptor>";
		VRaptorConfiguration config = getConfig(xml);
		assertEquals(1, config.getItems().size());
		assertTrue(config.getItems().get(0).getClass().equals(ComponentConfig.class));
	}

	public static class MyComponent {
	}

	public void testPluginUsingSimpleApproach() {
		String xml = "<vraptor><plugin>org.vraptor.config.XMLConfigurationLoaderTest$MyPlugin</plugin></vraptor>";
		VRaptorConfiguration config = getConfig(xml);
		assertEquals(1, config.getItems().size());
		assertTrue(config.getItems().get(0).getClass().equals(PluginConfig.class));
	}

	public void testPluginUsingProperties() throws ConfigException {
		String xml =
			"<vraptor>"
				+ "<plugin type=\"org.vraptor.config.XMLConfigurationLoaderTest$MyPlugin\">"
					+ "<property name=\"property1\">value1</property>"
				+ "</plugin>"
			+ "</vraptor>";
		VRaptorConfiguration config = getConfig(xml);
		assertEquals(1, config.getItems().size());
		assertTrue(config.getItems().get(0).getClass().equals(PluginConfig.class));

		PluginConfig pluginConfig = (PluginConfig) config.getItems().get(0);
		Mock webApp = mock(WebApplication.class);
		DefaultPluginManager pluginManager = new DefaultPluginManager();
		webApp.expects(once()).method("getPluginManager").withNoArguments().will(returnValue(pluginManager));
		pluginConfig.register((WebApplication) webApp.proxy());
		MyPlugin instance = (MyPlugin) pluginManager.getPlugins().get(0);
		assertEquals("value1", instance.properties.get("property1"));
	}

	public void testWriteConfigItens() throws Exception {
		String xml = "<vraptor>\n" +
				"  <plugin>org.vraptor.config.XMLConfigurationLoaderTest$MyPlugin</plugin>\n" +
				"</vraptor>";
		VRaptorConfiguration config = getConfig(xml);
		XMLConfigurationLoader loader = new XMLConfigurationLoader();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		loader.write(outputStream, config.getItems());
		assertEquals(xml, outputStream.toString());
	}
	public static class MyPlugin implements VRaptorPlugin {
		private final Map<String, String> properties;
		public MyPlugin(Map<String,String> properties) {
			this.properties = properties;

		}
		public void init(WebApplication application) throws VRaptorException {
		}
	}
}
