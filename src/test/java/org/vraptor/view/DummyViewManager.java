package org.vraptor.view;

import org.vraptor.LogicRequest;

public class DummyViewManager implements ViewManager {

	private String result;

	public void directForward(LogicRequest logicRequest, String result, String forwardUrl) throws ViewException {
		this.result = "directForward::" + result;
	}

	public void forward(LogicRequest logicRequest, String result) throws ViewException {
		this.result = "forward::" + result;
	}

	public void redirect(LogicRequest request, String result) throws ViewException {
		this.result = "redirect::" + result;
	}

	public String getResult() {
		return result;
	}

}
