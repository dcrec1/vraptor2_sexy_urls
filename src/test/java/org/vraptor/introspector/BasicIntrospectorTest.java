package org.vraptor.introspector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Read;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.DefaultLogicMethod;
import org.vraptor.component.DefaultLogicMethodFactory;
import org.vraptor.component.DefaultParameterInfoProvider;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.MethodParameter;
import org.vraptor.core.WebRequest;
import org.vraptor.i18n.ValidationMessage;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.reflection.SettingException;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.scope.ScopeType;

public class BasicIntrospectorTest extends AbstractTest {

	private static final String SOME_STRING = "SOME_STRING";

	public static class RequiredInjectionTest {
		@In
		private String defaultString;
	}

	public void testInjectsObjectThroughAnnotation() throws ComponentInstantiationException, SettingException {
		BasicIntrospector bi = new BasicIntrospector();
		LogicRequest context = createLogicRequest();
		context.getRequestContext().setAttribute("defaultString", SOME_STRING);
		RequiredInjectionTest test = new RequiredInjectionTest();
		bi.inject(ReflectionUtil.readAnnotations(RequiredInjectionTest.class, In.class), test, context);
		assertEquals(test.defaultString, SOME_STRING);
	}

	@SuppressWarnings("unchecked")
	public void testOutjectionByOutAnnotatedField() throws ComponentInstantiationException, SettingException,
			GettingException, MethodInvocationException, InvalidComponentException {
		BasicIntrospector bi = new BasicIntrospector();
		LogicRequest context = createLogicRequest();
		Field field = ReflectionUtil.getField(OutjectionTestClass.class, "x");
		bi.outject(context, new OutjectionTestClass(), createComponentType(OutjectionTestClass.class));
		assertEquals(5, context.getRequestContext().getAttribute("x"));
	}

	public void testOutjectionByCallingGetterMethod() throws ComponentInstantiationException, SettingException,
			GettingException, MethodInvocationException, InvalidComponentException {
		BasicIntrospector bi = new BasicIntrospector();
		LogicRequest context = createLogicRequest();
		bi.outject(context, new OutjectionTestClass(), createComponentType(OutjectionTestClass.class));
		assertEquals(3, context.getRequestContext().getAttribute("w"));
	}

	public static class OutjectionTestClass {
		@SuppressWarnings("unused")
		@Out
		private int x = 5;

		private int w = 3;

		public int getW() {
			return w;
		}
	}

	public void testRequiredInjectException() throws ComponentInstantiationException {
		BasicIntrospector bi = new BasicIntrospector();
		LogicRequest context = createLogicRequest();
		RequiredInjectionTest test = new RequiredInjectionTest();
		try {
			bi.inject(ReflectionUtil.readAnnotations(RequiredInjectionTest.class, In.class), test, context);
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public static class OptionalInjectionTest {
		@In(required = false)
		private String defaultString;
	}

	public void testOptionalInject() throws ComponentInstantiationException, SettingException {
		BasicIntrospector bi = new BasicIntrospector();
		LogicRequest context = createLogicRequest();
		OptionalInjectionTest test = new OptionalInjectionTest();
		bi.inject(ReflectionUtil.readAnnotations(OptionalInjectionTest.class, In.class), test, context);
		assertEquals(test.defaultString, null);
	}

	public static class InOutTestClass {
		@SuppressWarnings("unused")
		@Out
		@In
		int x;
	}

	@SuppressWarnings("unchecked")
	public void testFieldWithInAndOutAnnotations() throws ComponentInstantiationException, SettingException,
			GettingException, MethodInvocationException, InvalidComponentException {
		BasicIntrospector bi = new BasicIntrospector();
		LogicRequest context = createLogicRequest();
		context.getRequestContext().setAttribute("x", 5);

		// in
		InOutTestClass test = new InOutTestClass();
		bi.inject(ReflectionUtil.readAnnotations(InOutTestClass.class, In.class), test, context);
		assertEquals(test.x, 5);

		// out
		bi.outject(context, new OutjectionTestClass(), createComponentType(OutjectionTestClass.class));
		assertEquals(5, context.getRequestContext().getAttribute("x"));
	}

	public static class ScopedInjectionTest {
		@In(scope = ScopeType.SESSION)
		private String defaultString;
	}

	public void testScopedInject() throws ComponentInstantiationException, SettingException {
		BasicIntrospector bi = new BasicIntrospector();
		LogicRequest context = createLogicRequest();
		context.getSessionContext().setAttribute("defaultString", SOME_STRING);
		ScopedInjectionTest test = new ScopedInjectionTest();
		bi.inject(ReflectionUtil.readAnnotations(ScopedInjectionTest.class, In.class), test, context);
		assertEquals(test.defaultString, SOME_STRING);
	}

	public void testTriesToInjectFromWrongScope() throws ComponentInstantiationException {
		BasicIntrospector bi = new BasicIntrospector();
		LogicRequest context = createLogicRequest();
		context.getRequestContext().setAttribute("defaultString", SOME_STRING);
		ScopedInjectionTest test = new ScopedInjectionTest();
		try {
			bi.inject(ReflectionUtil.readAnnotations(ScopedInjectionTest.class, In.class), test, context);
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public static class ParamTestWithRead {
		@Read
		private String param;

		@Read
		private User user = new User();
	}

	public static class ParamTestWithParameter {
		@org.vraptor.annotations.Parameter
		private String param;

		@org.vraptor.annotations.Parameter
		private User user = new User();
	}

	public static class User {
		String name;

		int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class House {
		@In(required = false)
		String room = "base";
	}

	public void testDoesntSetFieldWhenNotRequiredAndNotFound() throws ComponentInstantiationException, SettingException {
		BasicIntrospector bi = new BasicIntrospector();
		House test = new House();
		bi.inject(ReflectionUtil.readAnnotations(House.class, In.class), test, createLogicRequest());
		assertEquals("base", test.room);
	}

	public void testUsesParameterWithReadAnnotation() throws InvalidComponentException, SettingException {
		ParamTestWithRead test = new ParamTestWithRead();
		readParams("param=1", test);
		assertEquals("1", test.param);
	}

	public void testUsesDepthParameterWithReadAnnotation() throws InvalidComponentException, SettingException {
		ParamTestWithRead test = new ParamTestWithRead();
		readParams("user.name=guilherme", test);
		assertEquals("guilherme", test.user.name);
	}

	public void testUsesParameterWithParameterAnnotation() throws InvalidComponentException, SettingException {
		ParamTestWithParameter test = new ParamTestWithParameter();
		readParams("param=1", test);
		assertEquals("1", test.param);
	}

	public void testUsesDepthParameterWithParameterAnnotation() throws InvalidComponentException, SettingException {
		ParamTestWithParameter test = new ParamTestWithParameter();
		readParams("user.name=guilherme", test);
		assertEquals("guilherme", test.user.name);
	}

	private <T> List<ValidationMessage> readParams(String param, T obj, String methodName, Object[] params) throws InvalidComponentException,
			SettingException {
		Method[] methods = obj.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				DefaultLogicMethod lm = new DefaultLogicMethodFactory(registry.getValidationErrorsFactory(), new DefaultParameterInfoProvider()).create(methodName, obj.getClass(), m);
				return readParams(param, obj, lm.getParameters(), params);
			}
		}
		fail("Method not found");
		return null;
	}

	private <T> List<ValidationMessage> readParams(String param, T obj, List<MethodParameter> extra, Object[] params)
			throws InvalidComponentException, SettingException {
		BasicIntrospector bi = new BasicIntrospector();
		ComponentType t = registry.getComponentManager().getComponentType(obj.getClass());
		LogicRequest request = new DefaultLogicRequest(null, new WebRequest(createRequest(param), createResponse(),
				createServletContext()));
		List<ReadParameter> paramsToRead = new ArrayList<ReadParameter>(t.getReadParameters());
		paramsToRead.addAll(extra);
		List<ValidationMessage> messages = bi.readParameters(paramsToRead, obj, request, createConverterManager(),
				params);
		return messages;
	}

	private <T> List<ValidationMessage> readParams(String param, T obj) throws InvalidComponentException, SettingException {
		return readParams(param, obj, new ArrayList<MethodParameter>(), new Object[0]);
	}

	public static class MethodParams {
		private int i;

		private String string;

		private User user;

		public void useInt(int i) {
			this.i = i;
		}

		public void useString(String s) {
			this.string = s;
		}

		public void useUser(User u) {
			this.user = u;
		}
	}

	public void testSetsPrimitiveMethodParameter() throws InvalidComponentException, SettingException {
		Object array[] = new Object[] { 15 };
		readParams("int=15", new MethodParams(), "useInt", array);
		assertEquals(15, array[0]);
	}

	public void testSetsStringMethodParameter() throws InvalidComponentException, SettingException {
		Object array[] = new Object[] { "" };
		readParams("string=15", new MethodParams(), "useString", array);
		assertEquals("15", array[0]);
	}

	public void testSetsDepthMethodParameter() throws InvalidComponentException, SettingException {
		User user = new User();
		readParams("user.name=guilherme", new MethodParams(), "useUser", new Object[] { user });
		assertEquals("guilherme", user.name);
	}

	public void testConvertsAWrongStringToAnInt() throws InvalidComponentException, SettingException {
		User user = new User();
		List<ValidationMessage> msgs = readParams("user.id=guilherme", new MethodParams(), "useUser", new Object[] { user });
		assertEquals(1, msgs.size());
		ValidationMessage message = msgs.get(0);
		assertEquals("user.id",message.getPath());
	}

	public static class CollectionTest {
		@In(create=true)
		private List<String> list;
	}

	public void testHandlesInstantiatingCollections() throws ComponentInstantiationException, SettingException {
		BasicIntrospector bi = new BasicIntrospector();
		LogicRequest context = createLogicRequest();
		CollectionTest test = new CollectionTest();
		bi.inject(ReflectionUtil.readAnnotations(CollectionTest.class, In.class), test, context);
		assertNotNull(test.list);
	}

}
