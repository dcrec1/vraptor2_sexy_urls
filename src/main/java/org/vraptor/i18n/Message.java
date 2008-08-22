package org.vraptor.i18n;

/**
 * A i18n message.
 * 
 * @author Guilherme Silveira
 */
public class Message implements ValidationMessage {

	private String message;

	private String[] parameters;

	private String category;

	private String path;

	public Message(String category, String message, String... parameters) {
		this.message = message;
		this.category = category;
		this.parameters = parameters;
		this.path = category;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Returns the message to be found
	 * 
	 * @return the message
	 */
	public String getKey() {
		return this.message;
	}

	public String[] getParameters() {
		return parameters;
	}

	/**
	 * @see org.vraptor.i18n.ValidationMessage#getCategory()
	 */
	public String getCategory() {
		return category;
	}
	
	public boolean isAlreadyLocalized() {
		return false;
	}

}
