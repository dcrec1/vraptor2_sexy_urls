package org.vraptor.component;

import org.apache.log4j.Logger;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.DefaultPicoContainer;
import org.vraptor.ValidationErrorsFactory;
import org.vraptor.scope.ApplicationContext;
import org.vraptor.scope.DefaultApplicationContext;
import org.vraptor.url.DefaultLogicLocator;
import org.vraptor.url.DefaultViewLocator;
import org.vraptor.url.LogicLocator;
import org.vraptor.url.ViewLocator;
import org.vraptor.validator.DefaultValidationErrorsFactory;
import org.vraptor.webapp.DefaultComponentManager;

import javax.servlet.ServletContext;

public class DefaultComponentContainer implements ComponentContainer {

    private final MutablePicoContainer container;

    private final static Logger LOG = Logger.getLogger(DefaultComponentContainer.class);

    private final static Class<?>[] TYPES = new Class<?>[]{ValidationErrorsFactory.class, LogicLocator.class, ComponentManager.class, LogicMethodFactory.class, ParameterInfoProvider.class, ViewLocator.class};

    public DefaultComponentContainer(ServletContext context) {
        this();
        container.registerComponentInstance(context);
        for (Class<?> type : TYPES) {
            String found = context.getInitParameter(type.getName());
            if (found != null) {
                try {
                    LOG.debug("Replacing default implementation of " + type.getName() + " with " + found);
                    container.unregisterComponent(type);
                    container.registerComponentImplementation(type, Class.forName(found));
                } catch (ClassNotFoundException e) {
                    LOG.error("Unable to load component implementation", e);
                }
            }
        }
    }

    public DefaultComponentContainer() {
        this.container = new DefaultPicoContainer();
        container.registerComponentImplementation(ValidationErrorsFactory.class, DefaultValidationErrorsFactory.class);
        container.registerComponentImplementation(ParameterInfoProvider.class, DefaultParameterInfoProvider.class);
        container.registerComponentImplementation(LogicMethodFactory.class, DefaultLogicMethodFactory.class);
        container.registerComponentImplementation(LogicLocator.class, DefaultLogicLocator.class);
        container.registerComponentImplementation(ComponentManager.class, DefaultComponentManager.class);
        container.registerComponentImplementation(ApplicationContext.class, DefaultApplicationContext.class);
        container.registerComponentImplementation(ViewLocator.class, DefaultViewLocator.class);
    }

    public ValidationErrorsFactory getValidationErrorsFactory() {
        return (ValidationErrorsFactory) container.getComponentInstance(ValidationErrorsFactory.class);
    }

    public LogicLocator getLogicLocator() {
        return (LogicLocator) container.getComponentInstanceOfType(LogicLocator.class);
    }

    public ComponentManager getComponentManager() {
        return (ComponentManager) container.getComponentInstanceOfType(ComponentManager.class);
    }

    public ApplicationContext getApplicationContext() {
        return (ApplicationContext) container.getComponentInstanceOfType(ApplicationContext.class);
    }

    public ViewLocator getViewLocator() {
        return (ViewLocator) container.getComponentInstanceOfType(ViewLocator.class);
    }

    @Deprecated
    public LogicLocator getURLManager() {
        return getLogicLocator();
    }

}
