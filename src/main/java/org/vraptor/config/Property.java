package org.vraptor.config;

/**
 * A configuration property.
 *
 * @author Rafael Steil
 */
public class Property {

	private final String name;

	private final String value;

	public Property(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}
}