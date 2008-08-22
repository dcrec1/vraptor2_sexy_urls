package org.vraptor.reflection;

import java.util.Collection;


/**
 * A set dealer factory.
 * 
 * @author Guilherme Silveira
 */
public class SetDealerFactory {

	public static SetDealer getDealer(Object object)
			throws SettingException {
		//		 TODO: optimize for random access where faster implementation is ok
		if (object.getClass().isArray()) {
			return new ArrayDealer();
		} else if (Collection.class.isAssignableFrom(object.getClass())) {
			return new CollectionDealer();
		}

		throw new SettingException("Invalid collection type: "
				+ object.getClass().getName());
	}

}
