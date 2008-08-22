package org.vraptor;

/**
 * Vraptor base exception.
 * 
 * @author Guilherme Silveira
 */
public class VRaptorException extends Exception {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = -8256392847941154867L;

	public VRaptorException() {
		super();
	}

	public VRaptorException(String message, Throwable cause) {
		super(message, cause);
	}

	public VRaptorException(String message) {
		super(message);
	}

	public VRaptorException(Throwable cause) {
		super(cause);
	}

}
