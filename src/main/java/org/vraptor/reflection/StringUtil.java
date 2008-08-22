package org.vraptor.reflection;

import java.beans.Introspector;

public class StringUtil {

	public static String classNameToInstanceName(String name) {		
		return Introspector.decapitalize(name);
	}

	public static String removeEnding(String string, String[] terminations) {
		for(String ending : terminations ) {
			if(string.endsWith(ending)) {
				return string.substring(0,string.length() - ending.length()); 
			}
		}
		return string;
	}
}
