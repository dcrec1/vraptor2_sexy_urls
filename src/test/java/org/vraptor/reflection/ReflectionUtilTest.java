package org.vraptor.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.vraptor.VRaptorException;
import org.vraptor.VRaptorFilter;
import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Read;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.FieldOutjecter;
import org.vraptor.component.GetterOutjecter;
import org.vraptor.component.Outjecter;

/**
 * Tests the reflection util class.
 *
 * @author Guilherme Silveira
 */
public class ReflectionUtilTest extends TestCase {

	public void testCompleteInstantiate() throws ComponentInstantiationException {
		ReflectionUtil.instantiate(VRaptorFilter.class);
	}

	@SuppressWarnings("unchecked")
	public void testInstantiateNullPointer() throws ComponentInstantiationException {
		try {
			ReflectionUtil.instantiate((Class) null);
			fail();
		} catch (Exception ex) {
			// ok
		}
	}

	public void instantiateArithmeticException() {
		try {
			ReflectionUtil.instantiate(WrongReflectionUtilTestClass.class);
			fail();
		} catch (ComponentInstantiationException e) {
			// ok
		}
	}

	public static class WrongReflectionUtilTestClass {
		public WrongReflectionUtilTestClass() throws VRaptorException {
			throw new VRaptorException("test exception");
		}
	}

	public void testMethodInvocation() throws MethodInvocationException, SecurityException, NoSuchMethodException {
		ReflectionUtilTestClass obj = new ReflectionUtilTestClass();
		ReflectionUtil.invoke(obj, obj.getClass().getMethod("simpleMethod"));
	}

	public void testWrongMethodInvocation() throws SecurityException, NoSuchMethodException {
		ReflectionUtilTestClass obj = new ReflectionUtilTestClass();
		try {
			ReflectionUtil.invoke(obj, obj.getClass().getMethod("simpleWrongMethod"));
			fail();
		} catch (MethodInvocationException e) {
			// ok
		}
	}

	public void testWrongArgumentMethodInvocation() throws SecurityException, NoSuchMethodException {
		ReflectionUtilTestClass obj = new ReflectionUtilTestClass();
		try {
			ReflectionUtil.invoke(obj, obj.getClass().getMethod("argumentMethod", int.class));
			fail();
		} catch (MethodInvocationException e) {
			// ok
		}
	}

	public void testArgumentMethodInvocation() throws MethodInvocationException, SecurityException,
			NoSuchMethodException {
		ReflectionUtilTestClass obj = new ReflectionUtilTestClass();
		ReflectionUtil.invoke(obj, obj.getClass().getMethod("argumentMethod", int.class), 1);
	}

	public void testSettingIllegalAccess() throws SettingException, SecurityException, NoSuchFieldException {
		try {
			ReflectionUtilTestClass obj = new ReflectionUtilTestClass();
			Field f = obj.getClass().getDeclaredField("internal");
			ReflectionUtil.setField(obj, f, 1);
			assert false : "Illegal Access.";
		} catch (SettingException e) {
			// ok
		}
	}

	public void testSettingInvalidType() throws SettingException, SecurityException, NoSuchFieldException {
		try {
			ReflectionUtilTestClass obj = new ReflectionUtilTestClass();
			Field f = obj.getClass().getDeclaredField("internal");
			boolean accessible = f.isAccessible();
			f.setAccessible(true);
			ReflectionUtil.setField(obj, f, "1");
			f.setAccessible(accessible);
			fail("Its an invalid type, should throw an exception.");
		} catch (SettingException e) {
			// ok
		}
	}

	public void testSetting() throws SettingException, SecurityException, NoSuchFieldException {
		ReflectionUtilTestClass obj = new ReflectionUtilTestClass();
		Field f = obj.getClass().getDeclaredField("internal");
		boolean accessible = f.isAccessible();
		f.setAccessible(true);
		ReflectionUtil.setField(obj, f, 1);
		f.setAccessible(accessible);
	}

	public void testFindSetter() throws SecurityException, NoSuchMethodException {
		Method setter = ReflectionUtil.findSetter(new ReflectionUtilTestClass(), "value");
		assertEquals(ReflectionUtilTestClass.class.getMethod("setValue", String.class), setter);
	}

	@SuppressWarnings("unused")
	@Read
	@Out
	@In
	private String annotatedField;

	public void testCannotFindAnnotation() {
		Annotation[] annotations = new Annotation[] {};
		assertNull(ReflectionUtil.findAnnotation(annotations, Read.class));
	}

	public void testChecksIfGetIsAGetter() throws SecurityException, NoSuchMethodException {
		Method get = ReflectionUtilTestClass.class.getMethod("get", new Class[0]);
		assertFalse(ReflectionUtil.isGetter(get));
	}

	public void testChecksIfIsIsAGetter() throws SecurityException, NoSuchMethodException {
		Method is = ReflectionUtilTestClass.class.getMethod("is", new Class[0]);
		assertFalse(ReflectionUtil.isGetter(is));
	}

	public void testChecksIfANonReturnMethodIsAGetter() throws SecurityException, NoSuchMethodException {
		Method getVoidProperty = ReflectionUtilTestClass.class.getMethod("getVoidProperty", new Class[0]);
		assertFalse(ReflectionUtil.isGetter(getVoidProperty));
	}

	public void testChecksIfAMethodWhichReceivesAParameterIsAGetter() throws SecurityException, NoSuchMethodException {
		Method getBizarre = ReflectionUtilTestClass.class.getMethod("getBizarre", new Class[] { Integer.TYPE });
		assertFalse(ReflectionUtil.isGetter(getBizarre));
	}

	public void testChecksIfAMethodNotStartingWithGetIsAGetter() throws SecurityException, NoSuchMethodException {
		Method bizarreGetter3 = ReflectionUtilTestClass.class.getMethod("bizarreGetter3", new Class[0]);
		assertFalse(ReflectionUtil.isGetter(bizarreGetter3));
	}

	public void testChecksIfAnIsMethodReturningStringIsAGetter() throws SecurityException, NoSuchMethodException {
		Method isBizarre = ReflectionUtilTestClass.class.getMethod("isBizarre", new Class[0]);
		assertFalse(ReflectionUtil.isGetter(isBizarre));
	}

	public void testChecksForAValidGetter() throws SecurityException, NoSuchMethodException {
		Method getInternal = ReflectionUtilTestClass.class.getMethod("getInternal", new Class[0]);
		assertTrue(ReflectionUtil.isGetter(getInternal));
	}

	public void testChecksForAValidIs() throws SecurityException, NoSuchMethodException {
		Method isClosed = ReflectionUtilTestClass.class.getMethod("isClosed", new Class[0]);
		assertTrue(ReflectionUtil.isGetter(isClosed));
	}

	public void testChecksForAStaticMethodGetter() throws SecurityException, NoSuchMethodException {
		Method getStatic = ReflectionUtilTestClass.class.getMethod("getStatic", new Class[0]);
		assertFalse(ReflectionUtil.isGetter(getStatic));
	}

	static class NonPublicClass {
	}

	public void testGetGettersWithNonPublicClass() {
		try {
			ReflectionUtil.getGetters(NonPublicClass.class);
			fail();
		} catch (IllegalArgumentException e) {
			// ok
		}
	}

	public void testGetGettersIgnoresGetClass() {
		Map<String, Method> x = ReflectionUtil.getGetters(ReflectionUtilTestClass.class);
		assertFalse(x.containsKey("class"));
	}

	public void testGetGettersIgnoresGettersAndIsersWithoutAName() {
		Map<String, Method> x = ReflectionUtil.getGetters(ReflectionUtilTestClass.class);
		assertFalse(x.containsKey(""));
	}

	public void testGetGettersIgnoresGettersReturningVoid() {
		Map<String, Method> x = ReflectionUtil.getGetters(ReflectionUtilTestClass.class);
		assertFalse(x.containsKey("voidProperty"));
	}

	public void testGetGettersFindsIs() {
		Map<String, Method> x = ReflectionUtil.getGetters(ReflectionUtilTestClass.class);
		assertTrue(x.containsKey("closed"));
	}

	public void testGetGettersForCapsPROPERTIES() {
		Map<String, Method> x = ReflectionUtil.getGetters(ReflectionUtilTestClass.class);
		assertTrue(x.containsKey("URLocationFoo"));
	}

	public void testGetGettersForFieldWithLiength1() {
		Map<String, Method> x = ReflectionUtil.getGetters(ReflectionUtilTestClass.class);
		assertTrue(x.containsKey("a"));
	}

	public static class ReflectionUtilTestClass {

		@SuppressWarnings("unused")
		private int internal;

		private boolean closed;

		public int getA() {
			return 0;
		}

		public void getVoidProperty() {
		}

		public void simpleMethod() {
		}

		public String getURLocationFoo() {
			return "";
		}

		public String is() {
			return null;
		}

		public void simpleWrongMethod() {
			@SuppressWarnings("unused")
			int i = 1 / 0;
		}

		public void argumentMethod(int i) {
		}

		public String isBizarre() {
			return null;
		}

		@SuppressWarnings("unused")
		private String value;

		public void setValue(String value) {
			this.value = value;
		}

		public static int getStatic() {
			return 0;
		}

		protected int getProtected() {
			return 0;
		}

		public int getInternal() {
			return internal;
		}

		public boolean isClosed() {
			return closed;
		}

		public void bizarreGetter1() {
		}

		public int bizarreGetter2(int x) {
			return x;
		}

		public int bizarreGetter3() {
			return 0;
		}

		public int getBizarre(int x) {
			return x;
		}

		public void get() {

		}

	}

	public static class AnnotatedField {
		@Out
		@SuppressWarnings("unused")
		private String field;
	}

	public void testLoadsAnnotatedFieldOutjecters() {
		List<Outjecter> outjecters = ReflectionUtil.loadOutjecters(AnnotatedField.class);
		assertEquals(1, outjecters.size());
		assertEquals(FieldOutjecter.class, outjecters.get(0).getClass());
	}

	public static class AnnotatedGetter {
		@Out
		public String getX() {
			return "";
		}
	}

	public void testLoadsAnnotatedGetterOutjecters() {
		List<Outjecter> outjecters = ReflectionUtil.loadOutjecters(AnnotatedGetter.class);
		assertEquals(1, outjecters.size());
		assertEquals(GetterOutjecter.class, outjecters.get(0).getClass());
	}

	public static class NotAnnotatedGetter {
		public String getX() {
			return "";
		}
	}

	public void testLoadsNotAnnotatedGetterOutjecters() {
		List<Outjecter> outjecters = ReflectionUtil.loadOutjecters(NotAnnotatedGetter.class);
		assertEquals(1, outjecters.size());
		assertEquals(GetterOutjecter.class, outjecters.get(0).getClass());
	}

}
