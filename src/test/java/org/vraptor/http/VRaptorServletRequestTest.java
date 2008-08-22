package org.vraptor.http;

import javax.servlet.http.HttpServletRequest;

import org.vraptor.AbstractTest;
import org.vraptor.introspector.BasicIntrospector;
import org.vraptor.test.MockedRequest;

public class VRaptorServletRequestTest extends AbstractTest {

	private HttpServletRequest request;

	public void setUp() {
		MockedRequest mock = createRequest();
		mock.setAttribute("wrapped", "true");
		this.request = new VRaptorServletRequest(mock, new BasicIntrospector());
	}

	/**
	 * Tries to get a wrapped attribute
	 *
	 */
	public void testGetWrappedAttribute() {
		assertEquals("Unable to get wrapped attribute", "true", this.request
				.getAttribute("wrapped"));
	}

	/**
	 * Gets a non-existing, non-factory-based attribute
	 *
	 */
	public void testGetsNull() {
		assertNull("Attribute should not exist!", this.request
				.getAttribute("qwerty"));
	}

}
