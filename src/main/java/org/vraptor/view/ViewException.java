package org.vraptor.view;

import org.vraptor.VRaptorException;

/**
 * Some exception occurred in your view. Grab the cause by calling the getCause
 * method
 * 
 * @author Guilherme Silveira
 */
public class ViewException extends VRaptorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2382459955911470297L;

	/**
	 * 
	 */
	public ViewException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ViewException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ViewException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ViewException(Throwable cause) {
		super(cause);
	}

}
