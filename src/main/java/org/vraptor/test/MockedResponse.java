package org.vraptor.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple mocked response.
 * 
 * @author Guilherme Silveira
 */
public class MockedResponse implements HttpServletResponse {

	
	private final StringWriter output;
	private final PrintWriter writer;
	private String contentType;
	private final ByteArrayOutputStream byteArrayOutputStream;

	public MockedResponse() {
		output = new StringWriter();
		writer = new PrintWriter(output);
		byteArrayOutputStream = new ByteArrayOutputStream();
	}
	public void addCookie(Cookie arg0) {
		// mock, does nothing
	}

	public boolean containsHeader(String arg0) {
		return false;
	}

	public String encodeURL(String url) {
		return null;
	}

	public String encodeRedirectURL(String arg0) {
		return null;
	}

	public String encodeUrl(String arg0) {
		return null;
	}

	public String encodeRedirectUrl(String arg0) {
		return null;
	}

	public void sendError(int arg0, String arg1) throws IOException {
		// mock, does nothing
	}

	public void sendError(int arg0) throws IOException {
		// mock, does nothing
	}

	public void sendRedirect(String arg0) throws IOException {
		// mock, does nothing
	}

	public void setDateHeader(String arg0, long arg1) {
		// mock, does nothing
	}

	public void addDateHeader(String arg0, long arg1) {
		// mock, does nothing
	}

	public void setHeader(String arg0, String arg1) {
		// mock, does nothing
	}

	public void addHeader(String arg0, String arg1) {
		// mock, does nothing
	}

	public void setIntHeader(String arg0, int arg1) {
		// mock, does nothing
	}

	public void addIntHeader(String arg0, int arg1) {
		// mock, does nothing
	}

	public void setStatus(int arg0) {
		// mock, does nothing
	}

	public void setStatus(int arg0, String arg1) {
		// mock, does nothing
	}

	public String getCharacterEncoding() {
		return null;
	}

	public String getContentType() {
		return this.contentType;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStream() {
			public void write(int b) throws IOException {
				byteArrayOutputStream.write(b);
			}
		};
	}

	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	public void setCharacterEncoding(String arg0) {
		// mock, does nothing
	}

	public void setContentLength(int arg0) {
		// mock, does nothing
	}

	public void setContentType(String c) {
		this.contentType = c;
	}

	public void setBufferSize(int arg0) {
		// mock, does nothing
	}

	public int getBufferSize() {
		return 0;
	}

	public void flushBuffer() throws IOException {
		// mock, does nothing
	}

	public void resetBuffer() {
		// mock, does nothing
	}

	public boolean isCommitted() {
		return false;
	}

	public void reset() {
		// mock, does nothing
	}

	public void setLocale(Locale arg0) {
		// mock, does nothing
	}

	public Locale getLocale() {
		return null;
	}
	
	public StringWriter getOutput() {
		return output;
	}
	
	public ByteArrayOutputStream getByteArrayOutputStream() {
		return byteArrayOutputStream;
	}

}
