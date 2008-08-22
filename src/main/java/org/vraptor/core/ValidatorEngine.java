package org.vraptor.core;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.component.LogicMethod;
import org.vraptor.i18n.JstlWrapper;
import org.vraptor.introspector.Introspector;
import org.vraptor.reflection.SettingException;
import org.vraptor.validator.UnstableValidationException;
import org.vraptor.validator.ValidationErrors;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Responsible for validating data.
 * 
 * @author Guilherme Silveira
 */
public class ValidatorEngine {

	private static final Logger LOG = Logger.getLogger(ValidatorEngine.class);

	private static final JstlWrapper JSTL = new JstlWrapper();

	public ValidationErrors validate(LogicMethod method, LogicRequest request, Introspector introspector,
			Object component, Object[] methodParamObjects) throws SettingException, UnstableValidationException {
		Locale locale = JSTL.findLocale(request);
		if (LOG.isDebugEnabled()) {
			LOG.debug("checking validation using " + method.getClass().getName());
		}

        ResourceBundle bundle = loadBundle(JSTL.findLocalizationContext(request), locale);
        ValidationErrors errors = method.validate(component, request, bundle, methodParamObjects);
		return errors;

	}

	private ResourceBundle loadBundle(String baseName, Locale locale) {
        if (LOG.isDebugEnabled()) {
			LOG.debug("loading resource bundle: " + baseName + " for locale " + locale);
		}
		try {
			return ResourceBundle.getBundle(baseName, locale);
		} catch (MissingResourceException ex) {
			LOG.debug("Resource bundle not found: " + baseName);
			return null;
		}
	}
}
