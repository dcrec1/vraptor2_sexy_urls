package org.vraptor.introspector;

import java.util.HashMap;
import java.util.Map;

import org.vraptor.LogicRequest;

public class MapBasedProvider implements BeanProvider {

	private final Map<String, Object> beans;

	public MapBasedProvider() {
		beans = new HashMap<String, Object>();
	}

	public Object findAttribute(LogicRequest context, String key) {
		return beans.get(key);
	}

	public void put(String key, Object obj) {
		beans.put(key, obj);
	}

}
