package org.vraptor.http;

import org.vraptor.test.MockedHttpSession;
import org.vraptor.test.MockedRequest;
import org.vraptor.test.MockedServletContext;

/**
 * A request factory.
 * 
 * @author Guilherme Silveira
 */
public class RequestFactory {

	public MockedRequest getRequest() {
		return new MockedRequest("component.logic.logic",
				new MockedHttpSession(new MockedServletContext()));
	}

}
