package org.vraptor.component;

import java.util.Map;

public interface LogicMethodFactory {

	Map<String, DefaultLogicMethod> loadLogics(Class<?> type) throws InvalidComponentException;

}