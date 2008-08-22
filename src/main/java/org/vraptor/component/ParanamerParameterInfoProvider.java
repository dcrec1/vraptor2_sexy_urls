package org.vraptor.component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class ParanamerParameterInfoProvider implements ParameterInfoProvider {
	
	private static final List<MethodParameter> EMPTY_LIST = new ArrayList<MethodParameter>();
	private final ParameterInfoProvider delegate = new DefaultParameterInfoProvider();
	private final Paranamer infoProvider = new CachingParanamer(new BytecodeReadingParanamer());
	private static final Logger LOGGER = Logger.getLogger(ParanamerParameterInfoProvider.class);

	public List<MethodParameter> provideFor(Method method) {
		if(method.getParameterTypes().length==0) {
			return EMPTY_LIST;
		}
		Class<?> declaringType = method.getDeclaringClass();
		List<MethodParameter> original = delegate.provideFor(method);
		if(infoProvider.areParameterNamesAvailable(declaringType, method.getName()) != Paranamer.PARAMETER_NAMES_FOUND) {
			return original;
		}
		String[] parameterNames = infoProvider.lookupParameterNames(method);
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Found parameter names with paranamer for " + method + " as " + parameterNames);
		}
		List<MethodParameter> modified = new ArrayList<MethodParameter>();
		int i = 0;
		for(MethodParameter p : original) {
			modified.add(new MethodParameter(p.getType(),p.getGenericType(), p.getPosition(), parameterNames[i++]));
		}
		return modified;
	}

}
