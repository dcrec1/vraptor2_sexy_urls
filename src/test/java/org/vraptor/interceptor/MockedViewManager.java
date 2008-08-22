package org.vraptor.interceptor;

import org.vraptor.LogicRequest;
import org.vraptor.view.ViewException;
import org.vraptor.view.ViewManager;

public class MockedViewManager implements ViewManager {

	private boolean redirected;

	private boolean directForwarded;

	private boolean forwarded;

	public void forward(LogicRequest logicRequest, String result) throws ViewException {
		this.forwarded = true;
	}

	public boolean isDirectForwarded() {
		return directForwarded;
	}

	public boolean isForwarded() {
		return forwarded;
	}

	public boolean isRedirected() {
		return redirected;
	}

	public void directForward(LogicRequest logicRequest, String result,

	String forwardUrl) throws ViewException {
		this.directForwarded = true;
	}

	public void redirect(LogicRequest request, String result) throws ViewException {
		this.redirected = true;
	}

}
