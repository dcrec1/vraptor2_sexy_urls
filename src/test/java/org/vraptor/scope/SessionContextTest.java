package org.vraptor.scope;

import org.vraptor.AbstractTest;

public class SessionContextTest extends AbstractTest {

	public void testSetAndGet() {
		SessionContext session = new SessionContext(createSession());
		session.setAttribute("basic", "nice");
		assertEquals("nice", session.getAttribute("basic"));
	}

	public void testHasAttribute() {
		SessionContext session = new SessionContext(createSession());
		session.setAttribute("blob", "nice");
		assert session.hasAttribute("blob") == true;
		assert session.hasAttribute("blobz") == false;
	}

	public void testDoesNotHaveAttribute() {
		SessionContext session = new SessionContext(createSession());
		assertFalse(session.hasAttribute("blob"));
	}

	public void testDoesNotHaveAttributeWhenGettingIt() {
		SessionContext session = new SessionContext(createSession());
		assertNull(session.getAttribute("blob"));
	}

	public void testRemoveNull() {
		SessionContext session = new SessionContext(createSession());
		assertNull(session.removeAttribute("blob"));
	}

	public void testRemoveAttribute() {
		SessionContext session = new SessionContext(createSession());
		session.setAttribute("blob", "nice");
		assertEquals(session.removeAttribute("blob"), "nice");
		assertFalse(session.hasAttribute("blob"));
	}

	public void testRemoveNullAttribute() {
		SessionContext context = new SessionContext(createSession());
		assertNull(context.removeAttribute("e"));
	}

}
