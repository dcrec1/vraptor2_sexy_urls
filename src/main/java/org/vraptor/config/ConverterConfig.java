package org.vraptor.config;

import org.apache.log4j.Logger;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.converter.Converter;
import org.vraptor.converter.ConverterManager;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.webapp.WebApplication;

/**
 * Models the xml portion to deal with component classes.
 * 
 * @author Guilherme Silveira
 */
public class ConverterConfig implements ConfigItem {

	private static final Logger LOG = Logger.getLogger(ConverterConfig.class);

	private Class<? extends Converter> type;

	@SuppressWarnings("unchecked")
	public <T> ConverterConfig(Class<T> clazz) {
		super();
		this.type = (Class<? extends Converter>) clazz;
	}

	public Class<?> getConverterClass() {
		return type;
	}

	public void register(WebApplication application)
			throws ConfigException {
		ConverterManager manager = application.getConverterManager();
		register(manager);
	}

	void register(ConverterManager manager) {
		try {
			Converter converter = (Converter) ReflectionUtil
					.instantiate(this.type);
			manager.register(converter);
		} catch (ComponentInstantiationException e) {
			LOG.error("Unable to instantiate and register converter", e);
		}
	}

	public boolean isComponent() {
		return true;
	}

	public boolean isManager() {
		return false;
	}

}
