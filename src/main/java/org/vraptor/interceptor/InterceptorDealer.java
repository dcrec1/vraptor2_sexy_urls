package org.vraptor.interceptor;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicRequest;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.introspector.Introspector;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.SettingException;

/**
 * Deals with the list of interceptors to be executed.
 * 
 * @author Guilherme Silveira
 * @author Paulo Silveira
 * @since 2.3.2
 */
public class InterceptorDealer {

	private static final Logger LOG = Logger.getLogger(InterceptorDealer.class);

	private final Introspector introspector;

	private final LinkedList<Interceptor> before;

	private final LinkedList<Interceptor> after;

	private final LinkedList<InterceptorType> main;

	private final LogicRequest logicRequest;

	public InterceptorDealer(LogicRequest logicRequest, Introspector introspector, List<Interceptor> before, List<InterceptorType> main, List<Interceptor> after) {
		this.logicRequest= logicRequest;
		this.introspector = introspector;
		this.before = new LinkedList<Interceptor>(before);
		this.after = new LinkedList<Interceptor>(after);
		this.main = new LinkedList<InterceptorType>(main);
	}

	public void outject(Interceptor interceptor, LogicRequest context) throws GettingException, MethodInvocationException {
		InterceptorType type = InterceptorType.getType(interceptor);
		introspector.outject(context, interceptor, type);
	}

	public void inject(Interceptor interceptor, LogicRequest context) throws ComponentInstantiationException,
			SettingException {
		this.introspector.inject(InterceptorType.getType(interceptor).getInAnnotations(), interceptor, context);
	}

	public Interceptor poll() throws InterceptorInstantiationException {
		if(!before.isEmpty()) {
			return before.poll();
		} else {
			if(!main.isEmpty()) {
				try {
					InterceptorType type = main.poll();
					Interceptor interceptor = type.newInstance(logicRequest, introspector.getBeanProvider());
					if (LOG.isDebugEnabled()) {
						LOG.debug("Adding interceptor " + interceptor.getClass().getName());
					}
					return interceptor;
				} catch (ComponentInstantiationException e) {
					throw new InterceptorInstantiationException(e.getMessage(), e);
				} catch (InvalidComponentException e) {
					throw new InterceptorInstantiationException(e.getMessage(), e);
				}
			} else {
				if(!after.isEmpty()) {
					return after.poll();
				} else {
					throw new IllegalStateException("logic flow execution being called more than"
							+ " than the number of interceptors. The last interceptor" + "must not continue the flow!");
				}
			}
		}
	}

	/**
	 * Adds an interceptor to the top.
	 * @param i	the interceptor to add
	 */
	public void add(InterceptorType i) {
		main.addFirst(i);
	}

}
