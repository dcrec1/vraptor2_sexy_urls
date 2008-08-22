package org.vraptor.url;

import org.vraptor.VRaptorException;

/**
 * Invalid url was requested. Unable to parse it.
 * 
 * @author Guilherme Silveira
 */
public class InvalidURLException extends VRaptorException {

	private static final long serialVersionUID = -2298864802081425313L;

	public InvalidURLException(String message) {
		super(message);
	}

}
