package org.vraptor.introspector;

import java.util.List;

import org.vraptor.LogicRequest;
import org.vraptor.annotations.In;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.component.Outjectable;
import org.vraptor.converter.ConverterManager;
import org.vraptor.i18n.ValidationMessage;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.SettingException;

/**
 * The instrospector is responsible for doing injection/outjection and pushing
 * parameters in the business logic.
 *
 * @author Guilherme Silveira
 */
public interface Introspector {

	/**
	 * Reads all parameters from the request and tries to fill the read
	 * annotation of a class.
	 *
	 * @param methodParamObjects
	 *
	 */
	List<ValidationMessage> readParameters(List<ReadParameter> parametersToRead, Object component,
			LogicRequest logicRequest, ConverterManager converterManager, Object[] methodParamObjects)
			throws SettingException;

	/**
	 * Call for injection: tries to inject each field listed on the
	 * inAnnotations on object component using the logic context passed as
	 * argument
	 *
	 * @param inAnnotations
	 *            annotations
	 * @param component
	 *            current object
	 * @param context
	 *            logic context
	 * @throws ComponentInstantiationException
	 *             unable to instantiate some component for the injection
	 * @throws SettingException
	 *             unable to set some field for the injection
	 */
	void inject(List<FieldAnnotation<In>> inAnnotations, Object component, LogicRequest context)
			throws ComponentInstantiationException, SettingException;

	void outject(LogicRequest logicRequest, Object component, Outjectable type) throws GettingException,
			MethodInvocationException;

	/**
	 * Returns the current bean provider for this introspector.
	 *
	 * @return the bean provider
	 * @since 2.2.4
	 */
	BeanProvider getBeanProvider();

	/**
	 * Overrides the current bean provider for this introspector.
	 *
	 * @param provider
	 *            the new bean provider
	 * @since 2.2.4
	 */
	void setBeanProvider(BeanProvider provider);

}