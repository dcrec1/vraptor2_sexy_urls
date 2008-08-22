package org.vraptor.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.webapp.WebApplication;

/**
 * Models the xml portion to deal with factory classes.
 *
 * @author Guilherme Silveira
 */
public class PluginConfig implements ConfigItem {

	private Class<?> pluginType;

	private final Map<String, String> properties;

	public PluginConfig(Class<?> type, Map<String, String> properties) {
		this.pluginType = type;
		this.properties = properties;
	}

	public Class<?> getPluginClass() {
		return pluginType;
	}

	/**
	 * Registers itself
	 *
	 * @see org.vraptor.config.ConfigItem#register(org.vraptor.webapp.DefaultWebApplication)
	 */
	@SuppressWarnings("unchecked")
	public void register(WebApplication application) throws ConfigException {
		try {
			Constructor constructor = findConstructor();
			Object instance;
			if(constructor!=null) {
				instance = constructor.newInstance(this.properties);
			} else {
				instance = ReflectionUtil.instantiate(this.pluginType);
			}
			application.getPluginManager().register((VRaptorPlugin) (instance));
		} catch (ComponentInstantiationException e) {
			throw new ConfigException(e.getMessage(), e);
		} catch (ClassCastException e) {
			throw new ConfigException("Did you forget to implement the plugin interface?", e);
		} catch (IllegalArgumentException e) {
			throw new ConfigException(e.getMessage(), e);
		} catch (InstantiationException e) {
			throw new ConfigException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new ConfigException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new ConfigException(e.getMessage(), e);
		}
	}

	private Constructor findConstructor() {
		try {
			return this.pluginType.getConstructor(Map.class);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 *
	 * @see org.vraptor.config.ConfigItem#isComponent()
	 */
	public boolean isComponent() {
		return true;
	}

	/**
	 *
	 * @see org.vraptor.config.ConfigItem#isManager()
	 */
	public boolean isManager() {
		return false;
	}
}
