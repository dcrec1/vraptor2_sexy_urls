package org.vraptor.webapp;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.vraptor.annotations.Component;
import org.vraptor.annotations.Destroy;
import org.vraptor.annotations.In;
import org.vraptor.annotations.Parameter;
import org.vraptor.annotations.Read;
import org.vraptor.component.BeanConstructor;
import org.vraptor.component.Clazz;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.DefaultComponentType;
import org.vraptor.component.DefaultLogicMethod;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicMethodFactory;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.introspector.FieldParameter;
import org.vraptor.introspector.FieldReadParameter;
import org.vraptor.introspector.ReadParameter;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.reflection.StringUtil;
import org.vraptor.scope.ScopeType;

/**
 * A simple implementation of a component manager.
 * 
 * @author Guilherme Silveira
 */
public class DefaultComponentManager implements ComponentManager {

	private static final Logger LOG = Logger.getLogger(DefaultComponentManager.class);

	public static final String VALIDATE_METHOD_INITIALS = "validate";

	public static final String[] COMPONENT_TERMINATIONS = { "Controller", "Logic", "Command", "Action", "Component"};

	private final ConcurrentMap<String, ConcurrentMap<String, ComponentType>> components = new ConcurrentHashMap<String, ConcurrentMap<String, ComponentType>>();

	private final LogicMethodFactory factory;

	public DefaultComponentManager(LogicMethodFactory factory) {
		this.factory = factory;
	}

	private List<ReadParameter> findParameters(Class<?> type) {
		ArrayList<ReadParameter> reads = new ArrayList<ReadParameter>();
		for (FieldAnnotation<Read> info : ReflectionUtil.readAnnotations(type, Read.class)) {
			reads.add(new FieldReadParameter(info));
		}
		for (FieldAnnotation<Parameter> info : ReflectionUtil.readAnnotations(type, Parameter.class)) {
			reads.add(new FieldParameter(info));
		}
		return reads;
	}
	
	public ComponentType getComponent(String name, String logic) throws ComponentNotFoundException, LogicNotFoundException {
		if (!this.components.containsKey(name)) {
			throw new ComponentNotFoundException(String.format("Component for %s not found", name));
		}
		if (!this.components.get(name).containsKey(logic)) {
			throw new LogicNotFoundException(String.format("Logic for %s not found in component %s", logic, name));
		}
		return this.components.get(name).get(logic);
	}

	private String getComponentName(Class<?> type) {
		String componentName;

		if (type.isAnnotationPresent(Component.class)) {
			Component ann = ((Component) type.getAnnotation(Component.class));
			if (!ann.value().equals("")) {
				componentName = ann.value();
			} else {
				String name = StringUtil.removeEnding(type.getSimpleName(), COMPONENT_TERMINATIONS);

				if (!name.equals(type.getSimpleName())) {
					componentName = name.toLowerCase();
				} else {
					componentName = type.getSimpleName();
				}
			}
		} else {
			componentName = type.getSimpleName();
		}

		return componentName;
	}

	public Set<ComponentType> getComponents() {
		HashSet<ComponentType> components = new HashSet<ComponentType>();
		for (Map<String, ComponentType> map : this.components.values()) {
			components.addAll(map.values());
		}
		return components;
	}
	
	public ComponentType getComponentType(Class<?> type) throws InvalidComponentException {
		String componentName = getComponentName(type);

		if (!type.isAnnotationPresent(Component.class)) {
			LOG.warn("Deprecated: registering " + type.getName() + " component without @Component annotation.");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Component found: " + componentName + " from " + type.getName());
		}

		ScopeType scope = getScope(type);

		Clazz clazz = new Clazz(type);
		BeanConstructor constructor = clazz.findSingleConstructor();
		LOG.debug("Registered constructor: " + constructor);

		Map<String, DefaultLogicMethod> actions = factory.loadLogics(type);

		List<InterceptorType> interceptors = InterceptorType.getInterceptors(type);

		// read fields
		List<FieldAnnotation<In>> ins = ReflectionUtil.readAnnotations(type, In.class);

		// destroy method
		String destroyLogicName = getDestroyLogicName(type);

		LOG.debug("Component clazz " + type.getName() + " read");

		List<ReadParameter> reads = findParameters(type);

		DefaultComponentType componentType = new DefaultComponentType(type, componentName, scope, constructor, actions, ins, interceptors,
				destroyLogicName, reads);
		for(DefaultLogicMethod method : actions.values()) {
			method.setComponentType(componentType);
		}
		return componentType;

	}

	private String getDestroyLogicName(Class<?> type) {
		String destroyLogicName = "destroy";
		for (Method m : type.getMethods()) {
			if (m.isAnnotationPresent(Destroy.class)) {
				destroyLogicName = m.getName();
			}
		}
		return destroyLogicName;
	}

	private ScopeType getScope(Class<?> type) {
		return type.isAnnotationPresent(Component.class) ? type.getAnnotation(Component.class).scope()
				: ScopeType.REQUEST;
	}

	public void register(ComponentType type) throws LogicNotFoundException {
		LOG.debug(String.format("Registering component %s as %s", type.getComponentClass(), type.getName()));
		if (!this.components.containsKey(type.getName())) {
			this.components.put(type.getName(), new ConcurrentHashMap<String, ComponentType>());
		}
		for (LogicMethod logic : type.getLogics()) {
			this.components.get(type.getName()).put(logic.getName(), type);
		}
	}

	public boolean register(String typeClazz) throws InvalidComponentException {
		try {
			Class<?> type = Class.forName(typeClazz);
			if (Modifier.isPublic(type.getModifiers())) {
				ComponentType component = getComponentType(type);
				register(component);
				return true;
			} else {
				LOG.warn("Ignoring non public class " + typeClazz);
				return false;
			}
		} catch (LogicNotFoundException e) {
			throw new InvalidComponentException("Unable to logic for type " + typeClazz, e);
		} catch (ClassNotFoundException e) {
			throw new InvalidComponentException("Unable to find type " + typeClazz, e);
		}
	}

}
