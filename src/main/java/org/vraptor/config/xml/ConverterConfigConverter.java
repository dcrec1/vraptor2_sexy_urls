package org.vraptor.config.xml;

import org.vraptor.config.ConverterConfig;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * A config converter xml converter (weird name...)
 * 
 * @author Guilherme Silveira
 */
public class ConverterConfigConverter implements Converter {

	/**
	 * Can it convert this class type?
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class clazz) {
		return clazz.equals(ConverterConfig.class);
	}

	/**
	 * Translates an object in the space to a xml string
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object,
	 *      com.thoughtworks.xstream.io.HierarchicalStreamWriter,
	 *      com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ConverterConfig object = (ConverterConfig) value;
		writer.setValue(object.getConverterClass().getName());
	}

	/**
	 * Translates xml to an object
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader,
	 *      com.thoughtworks.xstream.converters.UnmarshallingContext)
	 */
	@SuppressWarnings("unchecked")
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		try {
			String value = reader.getValue();
			if (value == null) {
				throw new NullPointerException();
			}
			Class<? extends Converter> clazz = (Class<? extends Converter>) Class
					.forName(value.trim());
			return new ConverterConfig(clazz);
		} catch (ClassCastException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}

}
