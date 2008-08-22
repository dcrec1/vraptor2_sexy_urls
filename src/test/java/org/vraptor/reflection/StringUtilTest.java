package org.vraptor.reflection;

import static org.vraptor.reflection.StringUtil.removeEnding;
import junit.framework.TestCase;

import org.vraptor.webapp.DefaultComponentManager;

public class StringUtilTest extends TestCase {

	public void testConvertsRealClassName() {
		assertEquals("name", StringUtil.classNameToInstanceName("Name"));
		assertEquals("URLocation", StringUtil.classNameToInstanceName("URLocation"));
		assertEquals("className", StringUtil.classNameToInstanceName("ClassName"));
		assertEquals("biZARREName", StringUtil.classNameToInstanceName("BiZARREName"));
		assertEquals("_name", StringUtil.classNameToInstanceName("_name"));
	}

	public void testRemoveEndings() {
		assertEquals("ProductList", removeEnding("ProductListLogic", DefaultComponentManager.COMPONENT_TERMINATIONS));
		assertEquals("Product", removeEnding("ProductLogic", DefaultComponentManager.COMPONENT_TERMINATIONS));
		assertEquals("ProductList", removeEnding("ProductListController", DefaultComponentManager.COMPONENT_TERMINATIONS));
		assertEquals("ProductList", removeEnding("ProductListComponent", DefaultComponentManager.COMPONENT_TERMINATIONS));
		assertEquals("ProductListNoTermination", removeEnding("ProductListNoTermination",
				DefaultComponentManager.COMPONENT_TERMINATIONS));
	}

}
