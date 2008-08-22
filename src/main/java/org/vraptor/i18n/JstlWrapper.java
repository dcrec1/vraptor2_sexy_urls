package org.vraptor.i18n;

import org.vraptor.LogicRequest;

import java.util.Locale;

public class JstlWrapper {
    private static final String DEFAULT_BUNDLE_NAME = "messages";

    public Object find(LogicRequest context, String name) {
		if (context.getRequestContext().hasAttribute(name + ".request")) {
			return context.getRequestContext().getAttribute(name + ".request");
		} else if (context.getSessionContext().hasAttribute(name + ".session")) {
			return context.getSessionContext().getAttribute(name + ".session");
		} else if (context.getApplicationContext().hasAttribute(name + ".application")) {
			return context.getApplicationContext().getAttribute(name + ".application");
		}
		return context.getRequest().getSession().getServletContext().getInitParameter(name);
	}

	public Locale findLocale(LogicRequest context) {
		Object obj = find(context, "javax.servlet.jsp.jstl.fmt.locale");
		if(obj instanceof String) {
			return stringToLocale((String) obj);
		} else if (obj != null){
			return (Locale) obj;
		} else {
			return context.getRequest().getLocale();
		}
	}

	/**
	 * Extracted from XStream project, copyright Joe Walnes
	 * @param str the string to convert
	 * @return	the locale
	 */
    public Locale stringToLocale(String str) {
        int[] underscorePositions = underscorePositions(str);
        String language, country, variant;
        if (underscorePositions[0] == -1) { // "language"
            language = str;
            country = "";
            variant = "";
        } else if (underscorePositions[1] == -1) { // "language_country"
            language = str.substring(0, underscorePositions[0]);
            country = str.substring(underscorePositions[0] + 1);
            variant = "";
        } else { // "language_country_variant"
            language = str.substring(0, underscorePositions[0]);
            country = str.substring(underscorePositions[0] + 1, underscorePositions[1]);
            variant = str.substring(underscorePositions[1] + 1);
        }
        return new Locale(language, country, variant);
    }

    private int[] underscorePositions(String in) {
        int[] result = new int[2];
        for (int i = 0; i < result.length; i++) {
            int last = i == 0 ? 0 : result[i - 1];
            result[i] = in.indexOf('_', last + 1);
        }
        return result;
    }

	public Locale findFallbackLocale(LogicRequest context) {
		Object obj = find(context, "javax.servlet.jsp.jstl.fmt.fallbackLocale");
		if(obj instanceof String) {
			return stringToLocale((String) obj);
		} else if (obj != null){
			return (Locale) obj;
		} else {
			return context.getRequest().getLocale();
		}
	}

    public String findLocalizationContext(LogicRequest request) {
        String localizationContext = (String) find(request, "javax.servlet.jsp.jstl.fmt.localizationContext");
        if(localizationContext == null) {
            localizationContext = DEFAULT_BUNDLE_NAME;
        }
        return localizationContext;
    }
}
