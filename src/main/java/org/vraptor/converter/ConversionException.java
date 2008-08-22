package org.vraptor.converter;

import org.vraptor.VRaptorException;
import org.vraptor.i18n.Message;
import org.vraptor.i18n.ValidationMessage;

/**
 * Basic convertion exception. Thrown when some conversion problem occurs i.e.
 * incompatibility or no converter found.
 * 
 * @author Guilherme Silveira
 */
public class ConversionException extends VRaptorException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 8239876883373338343L;

	private ValidationMessage i18nMessage;

	private String category;

	private String key;

	public ConversionException(String key, String message, Throwable cause) {
		super(message, cause);
		this.key = key;
	}

	public ConversionException(String key, String message) {
		super(message);
		this.key = key;
	}

	public ConversionException(Message message) {
		super(message.getKey());
		i18nMessage = message;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ValidationMessage getI18NMessage() {
		if (this.i18nMessage != null) {
			return this.i18nMessage;
		}
		return new Message(this.category, this.key);
	}

}
