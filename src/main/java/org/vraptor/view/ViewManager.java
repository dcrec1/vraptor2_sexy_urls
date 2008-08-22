package org.vraptor.view;

import org.vraptor.LogicRequest;

/**
 * A view manager decides where to go after an action has been executed.
 * 
 * @author Guilherme Silveira
 */
public interface ViewManager {

	/**
	 * Forwards the user based on some specific result
	 */
	void forward(LogicRequest logicRequest, String result) throws ViewException;

	/**
	 * Directly forwards to the selected forward url.
	 */
	void directForward(LogicRequest logicRequest, String result, String forwardUrl) throws ViewException;

	/**
	 * Sends a client redirect response.
	 */
	void redirect(LogicRequest request, String result) throws ViewException;

}
