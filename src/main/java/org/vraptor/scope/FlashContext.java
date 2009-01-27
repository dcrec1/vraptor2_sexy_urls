package org.vraptor.scope;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * A flash context lives through the current and following request only. It is
 * then copied to the request scope and replaced with a new empty
 * implementation.
 * 
 * @author Guilherme Silveira
 * @since 2.4
 */
public class FlashContext implements Context {

	private static final String KEY = ScopeType.class.getName() + "_"
			+ ScopeType.FLASH.name();

	private final HttpServletRequest request;

	public FlashContext(HttpServletRequest request) {
		this.request = request;
		if (getCurrentFlash() == null) {
			renew();
		}
	}

	public Object getAttribute(String name) {
		return getOldFlash().getAttribute(name);
	}

	private HttpServletRequest getOldFlash() {
		return request;
	}

	public boolean hasAttribute(String name) {
		return getOldFlash().getAttribute(name) != null;
	}

	public Object removeAttribute(String name) {
		throw new UnsupportedOperationException();
	}

	public void setAttribute(String name, Object value) {
		getCurrentFlash().put(name, value);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getCurrentFlash() {
		return (Map<String, Object>) request.getSession().getAttribute(KEY);
	}

	public void dumpToRequest() {
		Map<String, Object> currentFlash = getCurrentFlash();
		for (Map.Entry<String, Object> entry : currentFlash.entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}
	}

	public void renew() {
		this.request.getSession().setAttribute(KEY,
				new HashMap<String, Object>());
	}

}
