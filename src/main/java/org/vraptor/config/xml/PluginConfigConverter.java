package org.vraptor.config.xml;

import java.util.HashMap;
import java.util.Map;

import org.vraptor.config.PluginConfig;
import org.vraptor.config.Property;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * A config factory component xml converter
 *
 * @author Guilherme Silveira
 * @author Rafael Steil
 */
public class PluginConfigConverter implements Converter {

	/**
	 * Can it convert this class type?
	 *
	 * @see com.thoughtworks.xstream.converters.Converter#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class clazz) {
		return clazz.equals(PluginConfig.class);
	}

	/**
	 * Translates an object in the space to a xml string
	 *
	 * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object,
	 *      com.thoughtworks.xstream.io.HierarchicalStreamWriter,
	 *      com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		PluginConfig object = (PluginConfig) value;
		writer.setValue(object.getPluginClass().getName());
	}

	/**
	 * Translates xml to an object
	 */
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		try {
			if (reader.hasMoreChildren()) {
				return unmarshallWithProperties(reader, context);
			} else {
				return unmarshallWithoutProperties(reader, context);
			}
		} catch (ClassNotFoundException e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}

	private Object unmarshallWithoutProperties(HierarchicalStreamReader reader, UnmarshallingContext context)
			throws ClassNotFoundException {
		String value = reader.getValue();
		if (value == null) {
			throw new NullPointerException("The plugin element must have a value defined: the plugin type.");
		}
		return new PluginConfig(Class.forName(value.trim()), new HashMap<String,String>());
	}

	private Object unmarshallWithProperties(HierarchicalStreamReader reader, UnmarshallingContext context) throws ClassNotFoundException {
		String pluginClassName = reader.getAttribute("type");

		if (pluginClassName == null) {
			throw new NullPointerException("The plugin element must have a 'type' attribute");
		}

		Map<String,String> properties = new HashMap<String,String>();
		PluginConfig config = new PluginConfig(Class.forName(pluginClassName), properties);

		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (reader.getNodeName().equals("property")) {
				Property property = (Property) context.convertAnother(config, Property.class);
				properties.put(property.getName(), property.getValue());
			}
			reader.moveUp();
		}

		return config;
	}
}
