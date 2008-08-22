package org.vraptor.scope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * A simple request context.
 * 
 * @author Guilherme Silveira
 */
public class DefaultRequestContext implements RequestContext {

	private final HttpServletRequest request;
	private Set<String> attributeSet = new HashSet<String>();
 
	public DefaultRequestContext(HttpServletRequest request) {
		this.request = request;
	}

	public boolean hasAttribute(String name) {
		return request.getAttribute(name) != null;
	}

	public void setAttribute(String name, Object value) {
		attributeSet.add(name);
		request.setAttribute(name, value);
	}

	public Object getAttribute(String name) {
		return request.getAttribute(name);
	}

	public Object removeAttribute(String name) {
		attributeSet.remove(name);
		Object value = request.getAttribute(name);
		request.removeAttribute(name);
		return value;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getParameterMap() {
		return this.request.getParameterMap();
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the attributes that were set and not removed through
	 * this interface.
	 * 
	 * Note: this method will not return internal attributes registered
	 * by the servlet container that were not registered through this
	 * wrapper.
	 */
	public Map<String, Object> getAttributeMap() {
		Map<String, Object> attributes = new HashMap<String, Object>();
		for(String key : attributeSet) {
			attributes.put(key, request.getAttribute(key));
		}
		
		return attributes;
	}

}
