package org.vraptor.scope;

import org.vraptor.AbstractTest;
import org.vraptor.url.DefaultRequestInfo;

public class DefaultLogicRequestTest extends AbstractTest {

	public void testDoesntFindAttribute() {
		DefaultLogicRequest request = new DefaultLogicRequest(new DefaultRequestInfo("", ""), createWebRequest());
		assertNull(request.findAttribute("wicked_key"));
	}

	public void testFindsAttributeInRequestScope() {
		DefaultLogicRequest request = new DefaultLogicRequest(new DefaultRequestInfo("", ""), createWebRequest());
		request.getRequestContext().setAttribute("wicked_key", "someValue");
		assertEquals(request.findAttribute("wicked_key"), "someValue");
	}

	public void testFindsAttributeInSessionScope() {
		DefaultLogicRequest request = new DefaultLogicRequest(new DefaultRequestInfo("", ""), createWebRequest());
		request.getSessionContext().setAttribute("wicked_key", "someValue");
		assertEquals(request.findAttribute("wicked_key"), "someValue");
	}

	public void testFindsAttributeInApplicationScope() {
		DefaultLogicRequest request = new DefaultLogicRequest(new DefaultRequestInfo("", ""), createWebRequest());
		request.getApplicationContext().setAttribute("wicked_key", "someValue");
		assertEquals(request.findAttribute("wicked_key"), "someValue");
	}

	public void testFindsAttributeFirstInSessionScope() {
		DefaultLogicRequest request = new DefaultLogicRequest(new DefaultRequestInfo("", ""), createWebRequest());
		request.getSessionContext().setAttribute("wicked_key", "someValue");
		request.getApplicationContext().setAttribute("wicked_key", "otherValue");
		assertEquals(request.findAttribute("wicked_key"), "someValue");
	}

	public void testFindsAttributeFirstInRequestScope() {
		DefaultLogicRequest request = new DefaultLogicRequest(new DefaultRequestInfo("", ""), createWebRequest());
		request.getRequestContext().setAttribute("wicked_key", "someValue");
		request.getSessionContext().setAttribute("wicked_key", "otherValue");
		request.getApplicationContext().setAttribute("wicked_key", "otherValue");
		assertEquals(request.findAttribute("wicked_key"), "someValue");
	}

	public void testConfirmResultAndGetIt() {
		DefaultLogicRequest request = new DefaultLogicRequest(new DefaultRequestInfo("", ""), createWebRequest());
		request.confirmResult("result");
		assertEquals("result", request.getResult());
		try {
			request.confirmResult("newResult");
			fail();
		} catch (IllegalStateException e) {
		}
	}
}
