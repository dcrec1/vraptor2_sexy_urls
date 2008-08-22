package org.vraptor.scope;

import org.vraptor.AbstractTest;

public class DefaultApplicationContextTest extends AbstractTest {

	public void testRemovesAttributeReturnsItsOriginalValue() {
		DefaultApplicationContext context = new DefaultApplicationContext(createServletContext());
		context.setAttribute("name", "value");
		assertEquals("value", context.removeAttribute("name"));
		assertNull(context.getAttribute("name"));
	}

	public void testRemovesUnknownAttributeReturnsNull() {
		DefaultApplicationContext context = new DefaultApplicationContext(createServletContext());
		assertNull(context.removeAttribute("name"));
	}

}