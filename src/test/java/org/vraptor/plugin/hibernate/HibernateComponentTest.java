package org.vraptor.plugin.hibernate;

import org.hibernate.validator.NotNull;
import org.vraptor.AbstractTest;
import org.vraptor.component.DefaultLogicMethod;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;

public class HibernateComponentTest extends AbstractTest {

	public static class HouseLogic {
		@SuppressWarnings("unused")
		@NotNull
		private Long id1;

		@SuppressWarnings("unused")
		@NotNull
		private Long id2;

		@Validate(fields = { "id1", "id2" })
		public void action() {

		}

		public void noValidation() {

		}
	}

	public void testFindsALogicWithValidation()
			throws InvalidComponentException, LogicNotFoundException {
		HibernateComponent component = new HibernateComponent(
				registry.getComponentManager().getComponentType(HouseLogic.class),
				new ValidatorLocator());
		LogicMethod logic = component.getLogic("action");
		assertEquals(HibernateLogicMethod.class, logic.getClass());
	}

	public void testFindsALogicWithoutValidation()
			throws InvalidComponentException, LogicNotFoundException {
		HibernateComponent component = new HibernateComponent(
				registry.getComponentManager().getComponentType(HouseLogic.class),
				new ValidatorLocator());
		LogicMethod logic = component.getLogic("noValidation");
		assertEquals(DefaultLogicMethod.class, logic.getClass());
	}

}
