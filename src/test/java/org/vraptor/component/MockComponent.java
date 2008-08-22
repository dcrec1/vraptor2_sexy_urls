package org.vraptor.component;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.In;
import org.vraptor.annotations.Logic;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Read;

@Component("mock-valid")
@SuppressWarnings("unused")
public class MockComponent {

	@Read
	@Out
	private int read;

	@In
	@Out
	private int in;

	@Out
	@In
	private int out;

	@Logic("simple")
	public void valid() {

	}

}
