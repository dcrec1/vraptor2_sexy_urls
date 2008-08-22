package org.vraptor.interceptor;

import org.vraptor.LogicException;

/**
 * Unable to instantiate an interceptor.
 * 
 * @author Guilherme Silveira
 */
public class InterceptorInstantiationException extends LogicException {

	private static final long serialVersionUID = -628037460615779846L;

	public InterceptorInstantiationException(String message, Throwable cause) {
		super(message, cause);
	}

}
