package org.vraptor.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A parameter context capable of registering new parameters. Mainly used by
 * interceptors.
 * 
 * @author Guilherme Silveira
 */
public class RequestParameters {

	private final Map<String, Object> parameters = new HashMap<String, Object>();

	public RequestParameters(Map<String, Object> parameterMap) {
		parameters.putAll(parameterMap);
	}

	public void put(String name, String value) {
		parameters.put(name, value);
	}

	public Map<String, Object> getParameterMap() {
		return parameters;
	}

	public String getParameter(String name) {
		Object value = parameters.get(name);
		if (value == null) {
			return null;
		}
		if (value.getClass().isArray()) {
			return ((String[]) value)[0];
		}
		return (String) value;
	}

	public Enumeration getParameterNames() {
		return new Enumeration() {

			Iterator it = parameters.keySet().iterator();

			public boolean hasMoreElements() {
				return it.hasNext();
			}

			public Object nextElement() {
				return it.next();
			}

		};
	}

	public String[] getParameterValues(String name) {
		Object value = parameters.get(name);
		if (value == null) {
			return null;
		}
		if (value.getClass().isArray()) {
			return ((String[]) value);
		}
		return new String[] { (String) value };
	}

}
