package org.vraptor.url;

/**
 * Simple implementation of a request info.
 * 
 * @author Paulo Silveira
 * @author Guilherme Silveira
 * @since 2.2.3
 */
public class DefaultRequestInfo implements RequestInfo {

	private final String componentName;

	private final String logicName;

	/**
	 * @param componentName
	 *            the name of the Component to be used
	 * @param logicName
	 *            the logic name form the component that will be invoked
	 */
	public DefaultRequestInfo(String componentName, String logicName) {
		this.componentName = componentName;
		this.logicName = logicName;
	}

	/* (non-Javadoc)
	 * @see org.vraptor.url.RequestInfo#getComponentName()
	 */
	public String getComponentName() {
		return componentName;
	}

	/* (non-Javadoc)
	 * @see org.vraptor.url.RequestInfo#getLogicName()
	 */
	public String getLogicName() {
		return logicName;
	}

	@Override
	public String toString() {
		return String.format("[RequestInfo: %s, %s, %s]", componentName, logicName);
	}
}
