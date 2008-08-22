package org.vraptor.i18n;

/**
 * A validation message.
 * 
 * @author Guilherme Silveira
 * @since 2.1
 */
public interface ValidationMessage {

	String getCategory();

	/**
	 * Whether it has already been localized or not.
	 * 
	 * @return true if already localized.
	 */
	boolean isAlreadyLocalized();

	/**
	 * Sets this message path.
	 * 
	 * @param path
	 *            its path
	 * @since 2.3.1
	 */
	void setPath(String path);

	/**
	 * Returns this validation message path.
	 * 
	 * @return the path
	 */
	String getPath();

}