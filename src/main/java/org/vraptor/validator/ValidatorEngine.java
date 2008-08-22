package org.vraptor.validator;

import org.vraptor.LogicRequest;
import org.vraptor.component.LogicMethod;

public interface ValidatorEngine {

	void validate(Object component, LogicMethod method, LogicRequest request,
			ValidationErrors errors);

}
