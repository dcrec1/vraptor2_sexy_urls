package org.vraptor.component;

import java.io.IOException;

import org.vraptor.annotations.Component;

@Component("mock-invalid")
public class MockInvalidComponent {

	public MockInvalidComponent() throws IOException {
		throw new IOException("hummm");
	}

}
