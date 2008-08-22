package org.vraptor.interceptor;

import java.io.File;

import junit.framework.TestCase;

public class BasicUploadedFileInformationTest extends TestCase {

	public void testUnixPath() {
		BasicUploadedFileInformation info = new BasicUploadedFileInformation(
				new File("path/name"), "path/name", "contentType");
		assertEquals("name", info.getFileName());
	}

	public void testWindowsPath() {
		BasicUploadedFileInformation info = new BasicUploadedFileInformation(
				new File("path/name"), "path\\name", "contentType");
		assertEquals("name", info.getFileName());
	}
}
