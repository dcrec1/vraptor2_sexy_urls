package org.vraptor.config;

import junit.framework.TestCase;

public class RegexViewManagerConfigTest extends TestCase {

	public void testThrowsExceptionIfRegexIsNull() {
		try {
			RegexViewManagerConfig config = new RegexViewManagerConfig(null);
			fail("Should have thrown a NullPointerException");
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	public void testThatRegexIsTrimmed() {
		RegexViewManagerConfig config = new RegexViewManagerConfig(
				"  regex  \n");
		assertEquals("regex", config.getRegex());
	}
	
	public void testThatItIsNotAComponent(){
		RegexViewManagerConfig config = new RegexViewManagerConfig("");
		assertFalse(config.isComponent());
	}
	
	public void testThatItIsAManager(){
		RegexViewManagerConfig config = new RegexViewManagerConfig("");
		assertTrue(config.isManager());
	}

}
