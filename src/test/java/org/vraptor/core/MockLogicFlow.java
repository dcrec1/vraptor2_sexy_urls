package org.vraptor.core;

import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.RedirectException;
import org.vraptor.view.ViewException;

public class MockLogicFlow implements LogicFlow {

	private boolean executed;

	private boolean redirected;

	private String urlRedirected;

	private LogicRequest logicRequest;

	public MockLogicFlow(LogicRequest logicRequest) {
		super();
		this.logicRequest = logicRequest;
	}

	public void execute() throws ViewException, LogicException {
		executed = true;
	}

	public void redirect(String url) throws RedirectException {
		urlRedirected = url;
		redirected = true;
	}

	public boolean isExecuted() {
		return executed;
	}

	public boolean isRedirected() {
		return redirected;
	}

	public String getUrlRedirected() {
		return urlRedirected;
	}

	public LogicRequest getLogicRequest() {
		return this.logicRequest;
	}

}
