package org.vraptor.http;

import org.vraptor.AbstractTest;
import org.vraptor.test.MockedRequest;

public class RequestParametersTest extends AbstractTest {

	@SuppressWarnings("unchecked")
	public void testReadsAllParametersFromTheRequest() {
		MockedRequest request = createRequest("a=b");
		RequestParameters parameters = new RequestParameters(request
				.getParameterMap());
		assertEquals("b", ((String[]) parameters.getParameterMap().get("a"))[0]);
		assertEquals(1, parameters.getParameterMap().size());
	}

	@SuppressWarnings("unchecked")
	public void testOverwritesAParameter() {
		MockedRequest request = createRequest("a=b");
		RequestParameters parameters = new RequestParameters(request
				.getParameterMap());
		parameters.put("a", "guilherme");
		assertEquals("guilherme", parameters.getParameterMap().get("a"));
	}

	@SuppressWarnings("unchecked")
	public void testRegistersANewParameter() {
		MockedRequest request = createRequest();
		RequestParameters parameters = new RequestParameters(request
				.getParameterMap());
		parameters.put("username", "guilherme");
		assertEquals("guilherme", parameters.getParameterMap().get("username"));
	}

	@SuppressWarnings("unchecked")
	public void testGetParameterReturnsTheFirstParameter() {
		RequestParameters param = new RequestParameters(createRequest("a=b",
				"a=c").getParameterMap());
		String result = param.getParameter("a");
		assertTrue(result.equals(param.getParameterValues("a")[0]));
	}

	@SuppressWarnings("unchecked")
	public void testGetNullParameterReturnsNull() {
		RequestParameters param = new RequestParameters(createRequest()
				.getParameterMap());
		String result = param.getParameter("not-found");
		assertNull(result);
	}
}
