package org.vraptor.component;

import org.vraptor.VRaptorException;

/**
 * Invalid component.
 * 
 * @author Guilherme Silveira
 */
public class InvalidComponentException extends VRaptorException {

	private static final long serialVersionUID = -3516627684184824760L;

	public InvalidComponentException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidComponentException(String message) {
		super(message);
	}

}
