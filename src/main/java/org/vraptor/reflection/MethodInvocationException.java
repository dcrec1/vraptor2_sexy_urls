package org.vraptor.reflection;

import org.vraptor.VRaptorException;

/**
 * Method invocation problem.
 * 
 * @author Guilherme Silveira
 */
public class MethodInvocationException extends VRaptorException {

	private static final long serialVersionUID = -5620431708629544816L;

	public MethodInvocationException(String message, Throwable cause) {
		super(message, cause);
	}

}
