package org.vraptor.reflection;

import org.vraptor.VRaptorException;

/**
 * Exception while trying to set a field's value.
 * 
 * @author Guilherme Silveira
 */
public class SettingException extends VRaptorException {

	private static final long serialVersionUID = -5620431708629544816L;

	public SettingException(String message, Throwable cause) {
		super(message, cause);
	}

	public SettingException(String message) {
		super(message);
	}

}
