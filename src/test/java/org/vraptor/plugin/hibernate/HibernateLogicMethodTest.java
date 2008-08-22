package org.vraptor.plugin.hibernate;

import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;
import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.ValidationException;
import org.vraptor.component.DefaultLogicMethodFactory;
import org.vraptor.component.DefaultParameterInfoProvider;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.validator.UnstableValidationException;
import org.vraptor.validator.ValidationErrors;

public class HibernateLogicMethodTest extends AbstractTest {

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
	}

	private DefaultLogicMethodFactory factory;
	
	protected void setUp() throws Exception {
		super.setUp();
		this.factory = new DefaultLogicMethodFactory(registry.getValidationErrorsFactory(), new DefaultParameterInfoProvider());
	}

	public void testChecksValidFields() throws SecurityException, NoSuchMethodException, UnstableValidationException,
			ValidationException, InvalidComponentException {

		HouseLogic h = new HouseLogic();
		h.id1 = h.id2 = 1L;

		HibernateLogicMethod method = new HibernateLogicMethod(HouseLogic.class, factory.create(
				"action", HouseLogic.class, HouseLogic.class.getMethod("action")), new ValidatorLocator());

		ValidationErrors errors = method.validate(h, createLogicRequest(), null, new Object[] {});

		assertEquals(0, errors.size());

	}

	public void testChecksSingleInvalidField() throws SecurityException, NoSuchMethodException,
			UnstableValidationException, ValidationException, InvalidComponentException {

		HouseLogic h = new HouseLogic();
		h.id1 = 1L;

		HibernateLogicMethod method = new HibernateLogicMethod(HouseLogic.class, factory.create(
				"action", HouseLogic.class, HouseLogic.class.getMethod("action")), new ValidatorLocator());

		ValidationErrors errors = method.validate(h, createLogicRequest(), null, new Object[] {});

		assertEquals(1, errors.size());

	}

	public void testChecksTwoInvalidFields() throws SecurityException, NoSuchMethodException,
			UnstableValidationException, ValidationException, InvalidComponentException {

		HouseLogic h = new HouseLogic();

		HibernateLogicMethod method = new HibernateLogicMethod(HouseLogic.class, factory.create(
				"action", HouseLogic.class, HouseLogic.class.getMethod("action")), new ValidatorLocator());

		ValidationErrors errors = method.validate(h, createLogicRequest(), null, new Object[] {});

		assertEquals(2, errors.size());

	}

	public static class PersonLogic {

		@Valid
		private Person p = new Person();

		@Validate(fields = { "p.id1" })
		public void partial() {

		}

		@Validate(fields = { "p" })
		public void complete() {

		}
	}

	public static class Person {
		@SuppressWarnings("unused")
		@NotNull
		private Long id1;

		@SuppressWarnings("unused")
		@NotNull
		private Long id2;
	}

	public void testChecksCompleteValidationIsOk() throws SecurityException, NoSuchMethodException,
			UnstableValidationException, ValidationException, InvalidComponentException {

		PersonLogic l = new PersonLogic();
		l.p.id1 = l.p.id2 = 1L;

		HibernateLogicMethod method = new HibernateLogicMethod(PersonLogic.class, createLogicMethod(PersonLogic.class,
				"complete"), new ValidatorLocator());

		ValidationErrors errors = method.validate(l, createLogicRequest(), null, new Object[] {});

		assertEquals(0, errors.size());

	}

	public void testChecksCompleteValidationIsWrong() throws SecurityException, NoSuchMethodException,
			UnstableValidationException, ValidationException, InvalidComponentException {

		PersonLogic l = new PersonLogic();

		HibernateLogicMethod method = new HibernateLogicMethod(PersonLogic.class, createLogicMethod(PersonLogic.class,
				"complete"), new ValidatorLocator());

		ValidationErrors errors = method.validate(l, createLogicRequest(), null, new Object[] {});

		assertEquals(2, errors.size());

	}

	public void testChecksPartialValidationIsOk() throws SecurityException, NoSuchMethodException,
			UnstableValidationException, ValidationException, InvalidComponentException {

		PersonLogic l = new PersonLogic();
		l.p.id1 = 1L;

		HibernateLogicMethod method = new HibernateLogicMethod(PersonLogic.class, createLogicMethod(PersonLogic.class,
				"partial"), new ValidatorLocator());

		ValidationErrors errors = method.validate(l, createLogicRequest(), null, new Object[] {});

		assertEquals(0, errors.size());

	}

	public void testChecksPartialValidationIsWrong() throws SecurityException, NoSuchMethodException,
			UnstableValidationException, ValidationException, InvalidComponentException {

		PersonLogic l = new PersonLogic();

		HibernateLogicMethod method = new HibernateLogicMethod(PersonLogic.class, factory.create(
				"partial", PersonLogic.class, PersonLogic.class.getMethod("partial")), new ValidatorLocator());

		ValidationErrors errors = method.validate(l, createLogicRequest(), null, new Object[] {});

		assertEquals(1, errors.size());

	}

	public static class PersonParamLogic {

		@Validate(params = { "person.id1" })
		public void partial(Person p) {

		}

		@Validate(params = { "person" })
		public void complete(Person p) {

		}
	}

	public void testIfCompleteValidationOfParamOutjectsToRequest() throws InvalidComponentException,
			UnstableValidationException {
		PersonParamLogic l = new PersonParamLogic();
		Person p = new Person();
		p.id1 = p.id2 = null;

		HibernateLogicMethod method = new HibernateLogicMethod(PersonLogic.class, createLogicMethod(
				PersonParamLogic.class, "complete"), new ValidatorLocator());

		LogicRequest request = createLogicRequest();

		method.validate(l, request, null, new Object[] { p });

		Object attribute = request.getRequestContext().getAttribute("person");

		assertEquals(p, attribute);
	}

	public void testIfPartialValidationOfParamOutjectsToRequest() throws InvalidComponentException,
			UnstableValidationException {
		PersonParamLogic l = new PersonParamLogic();
		Person p = new Person();
		p.id1 = p.id2 = null;

		HibernateLogicMethod method = new HibernateLogicMethod(PersonLogic.class, createLogicMethod(
				PersonParamLogic.class, "partial"), new ValidatorLocator());

		LogicRequest request = createLogicRequest();

		method.validate(l, request, null, new Object[] { p });

		Object attribute = request.getRequestContext().getAttribute("person");

		assertEquals(p, attribute);
	}

	public void testChecksCompleteValidationParamIsOk() throws SecurityException, NoSuchMethodException,
			UnstableValidationException, ValidationException, InvalidComponentException {

		PersonParamLogic l = new PersonParamLogic();
		Person p = new Person();
		p.id1 = p.id2 = 1L;

		HibernateLogicMethod method = new HibernateLogicMethod(PersonLogic.class, createLogicMethod(
				PersonParamLogic.class, "complete"), new ValidatorLocator());

		ValidationErrors errors = method.validate(l, createLogicRequest(), null, new Object[] { p });

		assertEquals(0, errors.size());

	}

	public void testChecksCompleteValidationParamIsWrong() throws SecurityException, NoSuchMethodException,
			UnstableValidationException, ValidationException, InvalidComponentException {

		PersonParamLogic l = new PersonParamLogic();

		HibernateLogicMethod method = new HibernateLogicMethod(PersonLogic.class, createLogicMethod(
				PersonParamLogic.class, "complete"), new ValidatorLocator());

		ValidationErrors errors = method.validate(l, createLogicRequest(), null, new Object[] { new Person() });

		assertEquals(2, errors.size());

	}

	public void testChecksPartialValidationParamIsOk() throws SecurityException, NoSuchMethodException,
			UnstableValidationException, ValidationException, InvalidComponentException {

		PersonParamLogic l = new PersonParamLogic();
		Person p = new Person();
		p.id1 = 1L;

		HibernateLogicMethod method = new HibernateLogicMethod(PersonLogic.class, createLogicMethod(
				PersonParamLogic.class, "partial"), new ValidatorLocator());

		ValidationErrors errors = method.validate(l, createLogicRequest(), null, new Object[] { p });

		assertEquals(0, errors.size());

	}

	public void testChecksPartialValidationParamIsWrong() throws SecurityException, NoSuchMethodException,
			UnstableValidationException, ValidationException, InvalidComponentException {

		PersonParamLogic l = new PersonParamLogic();

		HibernateLogicMethod method = new HibernateLogicMethod(PersonLogic.class, createLogicMethod(
				PersonParamLogic.class, "partial"), new ValidatorLocator());

		ValidationErrors errors = method.validate(l, createLogicRequest(), null, new Object[] { new Person() });

		assertEquals(1, errors.size());

	}

}
