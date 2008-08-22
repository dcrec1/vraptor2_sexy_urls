package org.vraptor.url;

/**
 * Encapsulates the component name and logic name that will be invoked.
 * 
 * @author Paulo Silveira
 * @author Guilherme Silveira
 * @since 2.2.4
 */
public interface RequestInfo {

	/**
	 * Returns the desired component name.
	 * 
	 * @return the component name
	 */
	String getComponentName();

	/**
	 * Returns the logic name to be executed.
	 * 
	 * @return the logic name
	 */
	String getLogicName();

}