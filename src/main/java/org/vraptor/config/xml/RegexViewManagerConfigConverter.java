package org.vraptor.config.xml;

import org.vraptor.config.RegexViewManagerConfig;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * A regex view manager converter.
 * 
 * @author Guilherme Silveira
 */
public class RegexViewManagerConfigConverter implements Converter {

	/**
	 * Can it convert this class type?
	 */
	public boolean canConvert(Class clazz) {
		return clazz.equals(RegexViewManagerConfig.class);
	}

	/**
	 * Translates an object in the space to a xml string
	 */
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		RegexViewManagerConfig object = (RegexViewManagerConfig) value;
		writer.setValue(object.getRegex());
	}

	/**
	 * Translates xml to an object
	 */
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return new RegexViewManagerConfig(reader.getValue());
	}

}
