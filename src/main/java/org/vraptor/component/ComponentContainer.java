package org.vraptor.component;

import org.vraptor.ValidationErrorsFactory;
import org.vraptor.scope.ApplicationContext;
import org.vraptor.url.LogicLocator;
import org.vraptor.url.ViewLocator;

public interface ComponentContainer {

	ValidationErrorsFactory getValidationErrorsFactory();

	LogicLocator getLogicLocator();
	
	@Deprecated
	LogicLocator getURLManager();

	ComponentManager getComponentManager();

	ApplicationContext getApplicationContext();

	ViewLocator getViewLocator();

}
