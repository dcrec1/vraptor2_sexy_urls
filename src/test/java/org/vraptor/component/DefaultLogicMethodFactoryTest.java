package org.vraptor.component;

import java.util.ArrayList;
import java.util.Map;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.annotations.Logic;
import org.vraptor.validator.ValidationErrors;

public class DefaultLogicMethodFactoryTest extends AbstractTest {

	private DefaultLogicMethodFactory factory;
	private Mock provider;

	protected void setUp() throws Exception {
		super.setUp();
		provider = mock(ParameterInfoProvider.class);
		this.factory = new DefaultLogicMethodFactory(registry.getValidationErrorsFactory(), (ParameterInfoProvider) provider.proxy());
	}

	public static class Door {
		int i;

		public void open() {
			i = 1;
		}

		public void close() {
			i = 3;
		}

		public void validateClose(ValidationErrors errors) {
			i = 2;
		}

		public void close(int val) {
			i = val;
		}

		public void validateClose(ValidationErrors errors, int val) {
			i = -val;
		}

	}

	public void testFindsMethodWithValidationMethod() throws SecurityException, NoSuchMethodException, LogicException,
			InvalidComponentException {
		provider.expects(once()).method("provideFor").will(returnValue(new ArrayList<MethodParameter>()));
		DefaultLogicMethod method = factory.create("open", Door.class, Door.class
				.getMethod("open"));
		Door door = new Door();
		method.validate(door, null, null, new Door[] {});
		assertEquals(0, door.i);
	}

	public void testFindsMethodWithValidationMethodReceivingParameters() throws SecurityException, NoSuchMethodException, LogicException,
			InvalidComponentException {
		provider.expects(once()).method("provideFor").will(returnValue(new ArrayList<MethodParameter>()));
		DefaultLogicMethod method = factory.create("close", Door.class, Door.class.getMethod("close", new Class[]{int.class}));
		Door door = new Door();
		method.validate(door, null, null, new Object[] {999});
		assertEquals(-999, door.i);
	}

	public void testFindsMethodWithoutValidationMethod() throws SecurityException, NoSuchMethodException,
			LogicException, InvalidComponentException {
		provider.expects(once()).method("provideFor").will(returnValue(new ArrayList<MethodParameter>()));
		DefaultLogicMethod method = factory.create("close", Door.class, Door.class
				.getMethod("close"));
		Door door = new Door();
		method.validate(door, null, null, new Door[] {});
		assertEquals(2, door.i);
	}

	public void testTriesToLoadAnInvalidStaticLogic() throws InvalidComponentException {
		assertEquals(0, factory.loadLogics(InvalidStaticLogic.class).size());
	}

	public void testIgnoresNonPublicLogic() throws InvalidComponentException {
		assertEquals(0, factory.loadLogics(NonPublicLogic.class).size());
	}

	public void testAcceptsMethodsWithArguments() throws InvalidComponentException {
		provider.expects(once()).method("provideFor").will(returnValue(new ArrayList<MethodParameter>()));
		assertEquals(1, factory.loadLogics(LogicWithArgs.class).size());
	}

	public static class LogicWithArgs {
		public void close(int i) {
		}
	}

	public static class InvalidStaticLogic {
		public static void close() {
		}
	}

	public static class NonPublicLogic {
		protected void close() {
		}
	}

	public static class LogicWithDots {
		@Logic("my.name")
		public void execute() {

		}
	}

	public static class RenamedLogic {
		@Logic("_new_name_")
		public void execute() {

		}
	}

	public static class NotRenamedLogic {
		@Logic(parameters = "a")
		public void execute(int a) {

		}
	}

	public void testRejectsLogicsWithDots() throws SecurityException, NoSuchMethodException {
		try {
			factory.create("a.b", LogicWithDots.class, LogicWithDots.class.getMethod("execute"));
			fail("Should reject logic name with dot.");
		} catch (InvalidComponentException e) {
			// ok
		}
	}

	public void testAnnotatedLogicRenaming() throws InvalidComponentException {
		provider.expects(once()).method("provideFor").will(returnValue(new ArrayList<MethodParameter>()));
		Map<String, DefaultLogicMethod> map = factory.loadLogics(RenamedLogic.class);
		assertEquals(1, map.size());
		assertTrue(map.containsKey("_new_name_"));
		assertTrue(!map.containsKey("execute"));
	}

	public void testAnnotatedLogicNotRenamed() throws InvalidComponentException {
		provider.expects(once()).method("provideFor").will(returnValue(new ArrayList<MethodParameter>()));
		Map<String, DefaultLogicMethod> map = factory.loadLogics(NotRenamedLogic.class);
		assertEquals(1, map.size());
		assertTrue(map.containsKey("execute"));
	}

}
