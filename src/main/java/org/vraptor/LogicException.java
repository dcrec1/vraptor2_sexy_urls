package org.vraptor;

/**
 * Some exception occured in your business logic or interceptor. You can grab
 * the exception by looking for this exception's cause.
 * 
 * @author Guilherme Silveira
 */
public class LogicException extends VRaptorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8317655622991755986L;

	/**
	 * 
	 */
	public LogicException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LogicException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public LogicException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LogicException(Throwable cause) {
		super(cause);
	}

}
