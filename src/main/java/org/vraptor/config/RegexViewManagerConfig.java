package org.vraptor.config;

import org.vraptor.view.RegexViewManager;
import org.vraptor.webapp.WebApplication;

/**
 * Configures the view manager.
 * 
 * @author Guilherme Silveira
 */
public class RegexViewManagerConfig implements ConfigItem {

	private String regex;

	public RegexViewManagerConfig(String regex) {
		if (regex == null) {
			throw new NullPointerException();
		}
		this.regex = regex.trim();
	}

	/**
	 * @return the regex
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * Registers itself
	 * 
	 * @see org.vraptor.config.ConfigItem#register(org.vraptor.webapp.DefaultWebApplication)
	 */
	public void register(WebApplication application) throws ConfigException {
		application.setViewManager(new RegexViewManager(regex));
	}

	/**
	 * 
	 * @see org.vraptor.config.ConfigItem#isComponent()
	 */
	public boolean isComponent() {
		return false;
	}

	/**
	 * 
	 * @see org.vraptor.config.ConfigItem#isManager()
	 */
	public boolean isManager() {
		return true;
	}

}
