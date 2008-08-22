package org.vraptor.config;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

import org.vraptor.config.xml.ComponentConfigConverter;
import org.vraptor.config.xml.ConverterConfigConverter;
import org.vraptor.config.xml.PluginConfigConverter;
import org.vraptor.config.xml.PropertyConverter;
import org.vraptor.config.xml.RegexViewManagerConfigConverter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Reads the xml configuration file.
 *
 * @author Guilherme Silveira
 */
public class XMLConfigurationLoader {

	private final XStream xstream;

	/**
	 * Constructs an xstream reader.
	 */
	public XMLConfigurationLoader() {
		xstream = new XStream(new PureJavaReflectionProvider(), new DomDriver());
		xstream.registerConverter(new ComponentConfigConverter());
		xstream.registerConverter(new ConverterConfigConverter());
		xstream.registerConverter(new PluginConfigConverter());
		xstream.registerConverter(new RegexViewManagerConfigConverter());
		xstream.registerConverter(new PropertyConverter());
		xstream.alias("vraptor", VRaptorConfiguration.class);
		xstream.aliasField("auto-discover", VRaptorConfiguration.class, "autoDiscover");
		xstream.addImplicitCollection(VRaptorConfiguration.class, "items");
		xstream.alias("component", ComponentConfig.class);
		xstream.alias("plugin", PluginConfig.class);
		xstream.alias("converter", ConverterConfig.class);
		xstream.alias("regex-view-manager", RegexViewManagerConfig.class);
	}

	/**
	 * Writes all items in a config output.
	 *
	 * @param stream
	 *            stream output
	 * @param items
	 *            items
	 * @throws IOException
	 *             output exception
	 */
	@SuppressWarnings("unchecked")
	public void write(OutputStream stream, List<ConfigItem> items) throws IOException {
		PrintWriter writer = new PrintWriter(stream);
		XStream xstream = getXStream();
		ObjectOutputStream out = xstream.createObjectOutputStream(writer, "vraptor");
		for (ConfigItem item : items) {
			out.writeObject(item);
		}
		out.flush();
		out.close();
		writer.flush();
		writer.close();
	}

	/**
	 * Returns the xstream instance.
	 *
	 * @return the instance
	 */
	private XStream getXStream() {
		return xstream;
	}

	/**
	 * Reads vraptor configuration.
	 * @param xml	its xml code
	 * @return	the vraptor configuration
	 * @since 2.3.2
	 */
	public VRaptorConfiguration read(Reader reader) {
		XStream xstream = getXStream();
		return (VRaptorConfiguration) xstream.fromXML(reader);
	}

}
