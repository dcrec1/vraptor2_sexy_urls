package org.vraptor.test;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class MockedServletContext implements ServletContext {

	private final Map<String, Object> attributes = new HashMap<String, Object>();

	private final Map<String, String> initParameters = new HashMap<String, String>();

	private final String basicPath;

	public MockedServletContext() {
		this.basicPath = new File("").getAbsolutePath();
	}

	public MockedServletContext(String basicPath) {
		this.basicPath = basicPath;
	}

	public ServletContext getContext(String uripath) {
		return null;
	}

	public int getMajorVersion() {
		return 0;
	}

	public int getMinorVersion() {
		return 0;
	}

	public String getMimeType(String file) {
		return null;
	}

	public Set<String> getResourcePaths(String path) {
		File rootFolder = new File(new File(this.basicPath), path);
		
		if (!rootFolder.exists()) {
			throw new IllegalArgumentException("Resource Path does not exist " + rootFolder.getAbsolutePath());
		}
		
		Set<String> paths = new HashSet<String>();
		
		for(String fileName : rootFolder.list()) {
			
			if(new File(rootFolder,fileName).isDirectory()){
				fileName += "/";
			}
			paths.add(path + fileName);
		}
		return paths;
	}

	public URL getResource(String path) throws MalformedURLException {
		return null;
	}

	public InputStream getResourceAsStream(String path) {
		return null;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}

	public RequestDispatcher getNamedDispatcher(String name) {
		return null;
	}

	public Servlet getServlet(String name) throws ServletException {
		return null;
	}

	public Enumeration getServlets() {
		return null;
	}

	public Enumeration getServletNames() {
		return null;
	}

	public void log(String msg) {
	}

	public void log(Exception exception, String msg) {
	}

	public void log(String message, Throwable throwable) {
	}

	public String getRealPath(String path) {
		return new File(new File(basicPath), path).getAbsolutePath();
	}

	public String getServerInfo() {
		return null;
	}

	public void setInitParameter(String name, String value) {
		this.initParameters.put(name, value);
	}

	public String getInitParameter(String name) {
		return this.initParameters.get(name);
	}

	public Enumeration getInitParameterNames() {
		return new Enumeration<String>() {
			private Iterator<String> it = initParameters.keySet().iterator();

			public boolean hasMoreElements() {
				return it.hasNext();
			}

			public String nextElement() {
				return it.next();
			}
		};
	}

	public String getServletContextName() {
		return null;
	}

	public Object getAttribute(String arg0) {
		return attributes.get(arg0);
	}

	public void setAttribute(String arg0, Object arg1) {
		attributes.put(arg0, arg1);
	}

	public boolean hasAttribute(String key) {
		return attributes.containsKey(key);
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	public Enumeration getAttributeNames() {
		return null;
	}

}
