package org.vraptor.i18n;

/**
 * A fixed validation message.
 *
 * @author Guilherme Silveira
 * @author Nico Steppat
 * @since 2.1
 */
public class FixedMessage implements ValidationMessage {

	private final String category, message;
	private String path;


	/**
	 * Should not be used.
	 * Use FixedMessage(String path, String message, String category) instead.
	 *
	 * @param category
	 * @param message
	 */
	@Deprecated
	public FixedMessage(String category, String message) {
		super();
		this.category = category;
		this.message = message;
		this.path = category;
	}

	/**
	 *
	 * Creates a fixed message, which is already localized.
	 *
	 * @param path - complete attribute path in object hierachy
	 * @param message - the message, already localized, no fmt key here
	 * @param category - optional category
	 */
	public FixedMessage(String path, String message, String category) {
		this.path = path;
		this.message = message;
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public boolean isAlreadyLocalized() {
		return true;
	}

	public String getKey() {
		return message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
