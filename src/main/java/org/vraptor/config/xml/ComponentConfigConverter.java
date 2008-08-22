package org.vraptor.config.xml;

import org.vraptor.config.ComponentConfig;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * A config component xml converter.
 * 
 * @author Guilherme Silveira
 */
public class ComponentConfigConverter implements Converter {

	public boolean canConvert(Class clazz) {
		return clazz.equals(ComponentConfig.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ComponentConfig object = (ComponentConfig) value;
		writer.setValue(object.getTypeName());
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String value = reader.getValue();
		if (value == null) {
			throw new NullPointerException();
		}
		return new ComponentConfig(value.trim());
	}

}
