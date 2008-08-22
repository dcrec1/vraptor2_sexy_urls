package org.vraptor.remote.xml;

import org.vraptor.remote.OutjectionSerializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * A basic object to xml serializer using xstream. It stripts out the package
 * names for types which had no aliases.
 * 
 * @author Guilherme Silveira
 */
public class XMLSerializer implements OutjectionSerializer {

	private final XStream stream;

	public XMLSerializer() {
		stream = new XStream(new DomDriver()) {
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {

					@Override
					public String serializedClass(Class type) {
						String value = super.serializedClass(type);
						if (type.getName().replace('$', '-').equals(value)) {
							return type.getSimpleName();
						}
						return value;
					}
				};
			}
		};
		stream.setMode(XStream.NO_REFERENCES);
	}

	public CharSequence serialize(Object object) {
		return stream.toXML(object);
	}

}
