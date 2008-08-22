package org.vraptor.introspector;

/**
 * A request parameter.
 * 
 * @author Guilherme Silveira
 */
class Parameter implements Comparable<Parameter> {

	private String key;

	private String[] path;

	Parameter(String key) {
		this.key = key;
		this.path = key.split("[\\.\\[\\]]+");
	}

	String getKey() {
		return key;
	}

	String[] getPath() {
		return path;
	}

	public int compareTo(Parameter o) {
		int len = Math.min(path.length, o.path.length);
		for (int i = 0; i < len; i++) {
			if (Character.isDigit(path[i].charAt(0))) {
				if (!Character.isDigit(o.path[i].charAt(0))) {
					// the array has lesser priority
					return 1;
				}
				if (Integer.parseInt(path[i]) != Integer.parseInt(o.path[i])) {
					return Integer.parseInt(path[i])
							- Integer.parseInt(o.path[i]);
				}
			} else {
				if (Character.isDigit(o.path[i].charAt(0))) {
					// the array has lesser priority
					return -1;
				}
				if (!path[i].equals(o.path[i])) {
					return path[i].compareTo(o.path[i]);
				}
			}
		}
		if (path.length != o.path.length) {
			return path.length - o.path.length;
		}
		return 0;
	}
	
	boolean matches(String key) {
		return getPath()[0].equals(key);
	}
}
