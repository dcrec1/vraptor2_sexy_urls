package org.vraptor.scope;

/**
 * A simple context, similar to a map. 
 * 
 * @author Guilherme Silveira
 * @author Paulo Silveira
 */
public interface Context {
	/**
	 * Sets an attribute. Optional operation.
	 * 
	 * @param name
	 *            name
	 * @param value
	 *            value
	 */
	void setAttribute(String name, Object value);

	/**
	 * Retrieves an attribute
	 * 
	 * @param name
	 *            name
	 * @return value or null if not found
	 */
	Object getAttribute(String name);

	/**
	 * Whether it contains an attribute
	 * 
	 * @param name
	 *            name
	 * @return true if contained
	 */
	boolean hasAttribute(String name);

	/**
	 * Removes an attribute
	 * 
	 * @param name
	 *            name
	 * @return value
	 */
	Object removeAttribute(String name);
}
