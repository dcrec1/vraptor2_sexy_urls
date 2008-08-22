package org.vraptor;

/**
 * Some exception occured during redirection.
 * 
 * @author Guilherme Silveira
 */
public class RedirectException extends VRaptorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8317655622991755986L;

	/**
	 * 
	 */
	public RedirectException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RedirectException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public RedirectException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RedirectException(Throwable cause) {
		super(cause);
	}

}
