package org.vraptor.test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.vraptor.AbstractTest;

public class MockedServletContextTest extends AbstractTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File tmp = new File("vraptor-tmp");
		tmp.mkdirs();
		new File(tmp, "welcome.html").createNewFile();
		File catalog = new File(tmp, "catalog");
		catalog.mkdirs();
		new File(catalog, "/index.html").createNewFile();
		new File(catalog, "produto").mkdirs();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		File f = new File("vraptor-tmp");
		for (String s : new String[] { "catalog/index.html", "catalog/produto", "catalog/", "welcome.html", "" }) {
			File file = new File(f, s);
			if (!file.delete()) {
				throw new IOException("Unable to delete temporary file " + file.getAbsolutePath());
			}
		}
	}

	public void testGetsTheRealPathOfSomeFile() {
		String baseDir = "/baseDir";
		String file = "file";
		assertEquals("/baseDir/file", new MockedServletContext(baseDir).getRealPath(file));
	}

	@SuppressWarnings("unchecked")
	public void testUsesGetResourcePath() {

		Set<String> paths = new MockedServletContext("vraptor-tmp").getResourcePaths("/catalog/");
		assertTrue(paths.contains("/catalog/produto/"));
		assertTrue(paths.contains("/catalog/index.html"));
		assertEquals(2, paths.size());

	}

	public void testThrowsIllegalArgumentExceptionIfApplicationRootDirectoryCannotBeRead() {
		try {
			new MockedServletContext("_ILLEGAL_BASIC_PATH_").getResourcePaths("_ILLEGAL_PATH_");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

}
