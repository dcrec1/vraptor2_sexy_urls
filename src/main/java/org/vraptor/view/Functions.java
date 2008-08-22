package org.vraptor.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Simple functions for complex taglibs.
 *
 * @author Guilherme Silveira
 */
public class Functions {

	private static final Logger LOG = Logger.getLogger(Functions.class);

	public static Object getProperty(Object o, String field) {
		field = Character.toUpperCase(field.charAt(0))
				+ field.substring(1, field.length());
		try {
			return o.getClass().getMethod("get" + field).invoke(o);
		} catch (Exception e) {
			Functions.LOG.error("Unable to call getter", e);
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> setToList(Set<T> set) {
		if (set == null) {
			return null;
		}
		return new ArrayList<T>(set);
	}

	/**
	 * Receives a string and translates its line breaks to br tags
	 *
	 * @param str
	 *            the string
	 * @return the html string
	 */
	public static String stringToHtml(String str) {
		return str.replaceAll("\n", "<br />\n");
	}

}
