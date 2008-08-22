package org.vraptor.config.xml;

import org.vraptor.config.Property;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * A property and value converter. Used by plugins.
 *
 * @author Rafael Steil
 *
 */
public class PropertyConverter implements Converter {

	/**
	 * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object,
	 *      com.thoughtworks.xstream.io.HierarchicalStreamWriter,
	 *      com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
	}

	/**
	 * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader,
	 *      com.thoughtworks.xstream.converters.UnmarshallingContext)
	 */
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String name = reader.getAttribute("name");

		if (name == null) {
			throw new NullPointerException("The 'property' element of the 'plugin' element must have a 'name' attrite");
		}

		String value = reader.getValue();

		if (value == null) {
			throw new NullPointerException("The 'property' element of the 'plugin' element must have a value");
		}

		return new Property(name, value);
	}

	/**
	 * @see com.thoughtworks.xstream.converters.ConverterMatcher#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class clazz) {
		return clazz.equals(Property.class);
	}
}