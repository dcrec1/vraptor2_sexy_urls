package org.vraptor.component;

import org.vraptor.VRaptorException;

/**
 * Logic not found or invalid.
 * 
 * @author Guilherme Silveira
 */
public class LogicNotFoundException extends VRaptorException {

	private static final long serialVersionUID = 8107019768951502159L;

	public LogicNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogicNotFoundException(String message) {
		super(message);
	}

}
