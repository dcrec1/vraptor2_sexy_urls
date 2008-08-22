package org.vraptor.introspector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.In;
import org.vraptor.component.Clazz;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.component.Outjectable;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.ConverterManager;
import org.vraptor.i18n.ValidationMessage;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.JPathExecutor;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.reflection.SettingException;
import org.vraptor.scope.RequestContext;
import org.vraptor.scope.ScopeType;

/**
 * The default introspector implementation.
 * 
 * @author Guilherme Silveira
 */
public class BasicIntrospector implements Introspector {

	private static final Logger LOG = Logger.getLogger(BasicIntrospector.class);

	private final KeyExtractor keyExtractor = new KeyExtractor();

	private BeanProvider beanProvider;

	private final Map<Class, Clazz> clazzDirectory;

	public BasicIntrospector() {
		beanProvider = new WebBeanProvider();
		clazzDirectory = new HashMap<Class, Clazz>();
	}

	private Set<Parameter> addAll(Set<String> keys) {
		Set<Parameter> ts = new TreeSet<Parameter>();
		for (String key : keys) {
			ts.add(new Parameter(key));
		}
		return ts;
	}

	@SuppressWarnings("unchecked")
	public List<ValidationMessage> readParameters(List<ReadParameter> parametersToRead, Object component,
			LogicRequest logicRequest, ConverterManager converterManager, Object[] methodParamObjects)
			throws SettingException {

		JPathExecutor executor = new JPathExecutor(converterManager, logicRequest, methodParamObjects, component);
		RequestContext request = logicRequest.getRequestContext();

		Map<String, Object> parameters = request.getParameterMap();
		List<ValidationMessage> problems = new ArrayList<ValidationMessage>();

		out: for (Parameter p : addAll(parameters.keySet())) {
			String parameter = p.getKey();
			for (ReadParameter read : parametersToRead) {
				if (!p.matches(read.getKey())) {
					continue;
				}
				if (LOG.isDebugEnabled()) {
					LOG.debug("Parameter " + parameter + " will be used on field " + read.getKey());
				}
				try {
					// TODO: dont do this, cache it in request instead (or
					// use the internal request itself)
					executor.set(p.getPath(), singleValue(parameters.get(parameter)), arrayValue(parameters
							.get(parameter)), read);
				} catch (ConversionException e) {
					// validation problem...
					if (LOG.isDebugEnabled()) {
						LOG.debug(e.getMessage(), e);
					}
					ValidationMessage msg = e.getI18NMessage();
					msg.setPath(parameter);
					problems.add(msg);
				}
				continue out;
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("Parameter not used: " + parameter);
			}
		}

		return problems;

	}

	private String[] arrayValue(Object val) {
		if (val.getClass().isArray()) {
			return (String[]) val;
		}
		return new String[] { (String) val };
	}

	private String singleValue(Object val) {
		if (val.getClass().isArray()) {
			return (String) Array.get(val, 0);
		}
		return (String) val;
	}

	public void inject(List<FieldAnnotation<In>> inAnnotations, Object component, LogicRequest request)
			throws ComponentInstantiationException, SettingException {

		for (FieldAnnotation<In> info : inAnnotations) {
			In in = info.getAnnotation();
			String key = keyExtractor.extractInKey(info);
			Object value = in.scope().getContext(request).getAttribute(key);
			setFieldForInjection(component, info, in, key, value);
		}

	}

	private void setFieldForInjection(Object component, FieldAnnotation<In> info, In in, String key, Object value)
			throws ComponentInstantiationException, SettingException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Injecting with key " + key + " from " + in.scope());
		}

		if (value == null) {
			if (in.create()) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Trying to create " + info.getField().getType().getName());
				}
				value = getClazz(info.getField().getType()).newInstance();
			} else if (in.required()) {
				throw new SettingException("Unable to fill inject value for field " + info.getField().getName());
			} else {
				return;
			}
		}

		ReflectionUtil.setField(component, info.getField(), value);
	}

	/**
	 * Returns the clazz instance for the specified type.
	 * @param type	the type
	 * @return	the clazz instance
	 * @since 2.3.2
	 */
	private Clazz getClazz(Class<?> type) {
		// TODO is sync really worth it?
		if(!clazzDirectory.containsKey(type)) {
			clazzDirectory.put(type,new Clazz(type));
		}
		return clazzDirectory.get(type);
	}

	public void outject(LogicRequest logicRequest, Object component, Outjectable type) throws GettingException,
			MethodInvocationException {
		for (ScopeType scope : ScopeType.values()) {
			Map<String, Object> values = type.getOutjectedValues(component, scope);
			for (Entry<String, Object> value : values.entrySet()) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Outjecting key " + value.getKey() + " at " + scope);
				}
				scope.getContext(logicRequest).setAttribute(value.getKey(), value.getValue());
			}
		}
	}

	public BeanProvider getBeanProvider() {
		return beanProvider;
	}

	public void setBeanProvider(BeanProvider beanProvider) {
		this.beanProvider = beanProvider;
	}

}
