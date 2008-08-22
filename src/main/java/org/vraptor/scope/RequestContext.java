package org.vraptor.scope;

import java.util.Map;

/**
 * The request context.
 * 
 * @author Guilherme Silveira
 * 
 */
public interface RequestContext extends Context {

	Map<String, Object> getParameterMap();
	Map<String, Object> getAttributeMap();

}
