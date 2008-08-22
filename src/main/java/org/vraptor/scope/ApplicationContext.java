package org.vraptor.scope;

import java.util.Set;

public interface ApplicationContext extends Context {

	String getRealPath(String path);

	Set getResourcePaths(String directory);

}
