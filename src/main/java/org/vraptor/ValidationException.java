package org.vraptor;

/**
 * A validation error.
 * 
 * @author Guilherme Silveira
 */
public class ValidationException extends VRaptorException {

	private static final long serialVersionUID = 587920405512352876L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

}
