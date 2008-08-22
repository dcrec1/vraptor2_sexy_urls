package org.vraptor.config;

import org.vraptor.config.xml.ComponentConfigConverter;

import junit.framework.TestCase;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ConfigComponentConverterTest extends TestCase {

	public void testCanConvert() {
		assertTrue(new ComponentConfigConverter()
				.canConvert(ComponentConfig.class));
	}

	public void testMarshall() {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ComponentConfigConverter());
		xstream.alias("component", ComponentConfig.class);
		String expected = "<component>" + ConfigComponentTest.class.getName()
				+ "</component>";
		assertEquals(expected, xstream.toXML(new ComponentConfig(
				ConfigComponentTest.class.getName())));
	}

	public void testUnmarshallType() {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ComponentConfigConverter());
		xstream.alias("component", ComponentConfig.class);
		assertEquals(ComponentConfig.class, xstream.fromXML(
				String.format("<component>%s</component>",
						ConfigComponentTest.class.getName())).getClass());
	}

	public void testUnmarshall() {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ComponentConfigConverter());
		xstream.alias("component", ComponentConfig.class);
		ComponentConfig component = (ComponentConfig) xstream.fromXML(String
				.format("<component>%s</component>", ConfigComponentTest.class
						.getName()));
		assertEquals(ConfigComponentTest.class.getName(), component
				.getTypeName());
	}
}
