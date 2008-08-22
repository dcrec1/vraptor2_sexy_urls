package org.vraptor.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Simple mocked request.
 * 
 * @author Guilherme Silveira
 */
public class MockedRequest implements HttpServletRequest {

	private static final Logger logger = Logger.getLogger(MockedRequest.class);

	private Map<String, Object> attributes = new HashMap<String, Object>();

	private Map<String, Object> parameters = new HashMap<String, Object>();
	
	private Map<String, String> headers = new HashMap<String, String>();

	private HttpSession session;

	private String uri;

	public MockedRequest(String uri, HttpSession session, String... parameters) {
		this.uri = uri;
		this.session = session;
		for (String value : parameters) {
			String param[] = value.split("=");
			if (this.parameters.containsKey(param[0])) {
				Object current = this.parameters.get(param[0]);
				current = insert((String[]) current, param[1]);
				this.parameters.put(param[0], current);
			} else if (param.length == 1) {
				this.parameters.put(param[0], new String[] { "" });
			} else {
				this.parameters.put(param[0], new String[] { param[1] });
			}
		}
		// Config.set(session, Config.FMT_LOCALE, Locale.getDefault());
	}

	private String[] insert(String[] current, String newValue) {
		String[] newValues = new String[current.length + 1];
		for (int i = 0; i < current.length; i++) {
			newValues[i] = current[i];
		}
		newValues[current.length] = newValue;
		return newValues;
	}

	public void setAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}

	public void removeAttribute(String key) {
		this.attributes.remove(key);
	}

	public Object getAttribute(String key) {
		return this.attributes.get(key);
	}

	public Enumeration getAttributeNames() {
		return new Enumeration() {

			private Iterator<String> it = attributes.keySet().iterator();

			public boolean hasMoreElements() {
				return it.hasNext();
			}

			public Object nextElement() {
				return it.next();
			}

		};
	}

	public String getParameter(String key) {
		return ((String[]) this.parameters.get(key))[0];
	}

	public Enumeration getParameterNames() {
		return new Enumeration() {

			private Iterator<String> it = parameters.keySet().iterator();

			public boolean hasMoreElements() {
				return it.hasNext();
			}

			public Object nextElement() {
				return it.next();
			}

		};
	}

	public String[] getParameterValues(String key) {
		return (String[]) this.parameters.get(key);
	}

	public Map getParameterMap() {
		return this.parameters;
	}

	public String getAuthType() {
		return null;
	}

	public Cookie[] getCookies() {
		return new Cookie[] {};
	}

	public long getDateHeader(String arg0) {
		return 0;
	}

	public String getHeader(String key) {
		return headers.get(key);
	}

	public Enumeration getHeaders(String arg0) {
		return null;
	}

	public Enumeration getHeaderNames() {
		return null;
	}

	public int getIntHeader(String arg0) {
		return 0;
	}

	public String getMethod() {
		return null;
	}

	public String getPathInfo() {
		return null;
	}

	public String getPathTranslated() {
		return null;
	}

	public String getContextPath() {
		return null;
	}

	public String getQueryString() {
		return null;
	}

	public String getRemoteUser() {
		return null;
	}

	public boolean isUserInRole(String arg0) {
		return false;
	}

	public Principal getUserPrincipal() {
		return null;
	}

	public String getRequestedSessionId() {
		return null;
	}

	public String getRequestURI() {
		return this.uri;
	}

	public StringBuffer getRequestURL() {
		return null;
	}

	public String getServletPath() {
		return null;
	}

	public HttpSession getSession(boolean arg0) {
		return this.session;
	}

	public HttpSession getSession() {
		return this.session;
	}

	public boolean isRequestedSessionIdValid() {
		return false;
	}

	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}

	public boolean isRequestedSessionIdFromURL() {
		return false;
	}

	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}

	public String getCharacterEncoding() {
		return null;
	}

	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
	}

	public int getContentLength() {
		return 0;
	}

	public String getContentType() {
		return null;
	}

	public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	public String getProtocol() {
		return null;
	}

	public String getScheme() {
		return null;
	}

	public String getServerName() {
		return null;
	}

	public int getServerPort() {
		return 0;
	}

	public BufferedReader getReader() throws IOException {
		return null;
	}

	public String getRemoteAddr() {
		return null;
	}

	public String getRemoteHost() {
		return null;
	}

	public Locale getLocale() {
		return new Locale("pt", "BR");
	}

	public Enumeration getLocales() {
		return new Enumeration() {

			private int i = 0;

			public boolean hasMoreElements() {
				return i == 0;
			}

			public Object nextElement() {
				i = 1;
				return new Locale("pt", "BR");
			}

		};
	}

	public boolean isSecure() {
		return false;
	}

	public RequestDispatcher getRequestDispatcher(final String to) {
		return new RequestDispatcher() {

			public void forward(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
				logger.debug("forwarding to " + to);
			}

			public void include(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
				logger.debug("including " + to);
			}

		};
	}

	public String getRealPath(String arg0) {
		return null;
	}

	public int getRemotePort() {
		return 0;
	}

	public String getLocalName() {
		return null;
	}

	public String getLocalAddr() {
		return null;
	}

	public int getLocalPort() {
		return 0;
	}
	
	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

}
