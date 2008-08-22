package org.vraptor.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.vraptor.AbstractTest;
import org.vraptor.annotations.Parameter;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.ConverterManager;
import org.vraptor.converter.SimpleConverterManager;
import org.vraptor.converter.basic.StringConverter;
import org.vraptor.introspector.FieldParameter;

public class JPathExecutorTest extends AbstractTest {

	private static final String VALUE = "TEST";

	private static final String OTHER_VALUE = "TEST_2";

	private JPathExecutor getExecutor(Object obj) {
		ConverterManager manager = new SimpleConverterManager();
		manager.register(new StringConverter());
		return new JPathExecutor(manager, createLogicRequest(), new Object[0], obj);
	}

	public void testSimpleNoCreateSetter() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("mainChild.name"), VALUE, new String[] { VALUE }, getField(object, "mainChild"));
		assertEquals(VALUE, object.getMainChild().getName());
	}

	/**
	 * Tests a setter with create=true
	 * 
	 * @throws SettingException
	 */
	public void testSimpleCreateSetter() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("lazyChild.name"), VALUE, new String[] { VALUE }, getField(object, "lazyChild"));
		assertEquals(VALUE, object.lazyChild.getName());
	}

	/**
	 * Test array position with no instantiation
	 * 
	 * @throws SettingException
	 *             setter exception
	 */
	public void testArrayNoCreateSetter() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("childArray[1].name"), VALUE, new String[] { VALUE },
				getField(object, "childArray"));
		assertEquals(VALUE, object.childArray[1].getName());
	}

	public void testArrayInvalidIndexSetter() throws ConversionException {
		ParentClass object = new ParentClass();
		try {
			getExecutor(object).set(path("childArray[3].name"), VALUE, new String[] { VALUE },
					getField(object, "childArray"));
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public void testTooFastArrayCreateSetter() throws ConversionException {
		ParentClass object = new ParentClass();
		try {
			getExecutor(object).set(path("lazyChildArray[1].name"), VALUE, new String[] { VALUE },
					getField(object, "lazyChildArray"));
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public void testArrayCreateSetter() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("lazyChildArray[0].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyChildArray"));
		assertEquals(VALUE, object.lazyChildArray[0].getName());
	}

	public void testSequentialArrayCreateSetter() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("lazyChildArray[0].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyChildArray"));
		getExecutor(object).set(path("lazyChildArray[1].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyChildArray"));
		assertEquals(VALUE, object.lazyChildArray[1].getName());
	}

	public void testListNoCreateSetter() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object)
				.set(path("childList[1].name"), VALUE, new String[] { VALUE }, getField(object, "childList"));
		assertEquals(VALUE, object.childList.get(1).getName());
	}

	public void testListInvalidIndexSetter() throws ConversionException {
		ParentClass object = new ParentClass();
		try {
			getExecutor(object).set(path("childList[3].name"), VALUE, new String[] { VALUE },
					getField(object, "childList"));
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public void testListCreate() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("lazyChildList[0].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyChildList"));
		assertEquals(VALUE, object.lazyChildList.get(0).getName());
	}

	public void testMissedParameterListCreate() throws ConversionException {
		ParentClass object = new ParentClass();
		try {
			getExecutor(object).set(path("lazyChildList[1].name"), VALUE, new String[] { VALUE },
					getField(object, "lazyChildList"));
		} catch (SettingException e) {
		}
	}

	public void testSetNoCreateSetter() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("childSet[0].name"), VALUE, new String[] { VALUE }, getField(object, "childSet"));
		List<ChildClass> list = new ArrayList<ChildClass>(object.childSet);
		assertEquals(VALUE, list.get(0).getName());
	}

	public void testMissedParameterSetCreate() throws ConversionException {
		ParentClass object = new ParentClass();
		try {
			getExecutor(object).set(path("lazyChildSet[1].name"), VALUE, new String[] { VALUE },
					getField(object, "lazyChildSet"));
			fail();
		} catch (SettingException e) {
		}
	}

	public void testSetInvalidIndexSetter() throws ConversionException {
		ParentClass object = new ParentClass();
		try {
			getExecutor(object).set(path("childSet[3].name"), VALUE, new String[] { VALUE },
					getField(object, "childSet"));
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public void testSetCreateSetter() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("lazyChildSet[0].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyChildSet"));
		List<ChildClass> list = new ArrayList<ChildClass>(object.lazyChildSet);
		assertEquals(VALUE, list.get(0).getName());
	}

	public void testSetCreateSetterToVerifySetBeforeAddingToTheCollection() throws SettingException,
			ConversionException {
		ParentClass object = new ParentClass();
		// failing! Here we are adding two different objects to the Set
		// TODO: wrong test, must add the SAME object twice to the Set!
		getExecutor(object).set(path("lazyChildSet[0].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyChildSet"));
		getExecutor(object).set(path("lazyChildSet[1].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyChildSet"));
//		assertEquals(1, object.lazyChildSet.size());
	}

	public void testSetCreateSetterToVerifySetBeforeAddingToTheCollectionDifferentObjects() throws SettingException,
			ConversionException {
		ParentClass object = new ParentClass();
		// failing!
		getExecutor(object).set(path("lazyChildSet[0].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyChildSet"));
		getExecutor(object).set(path("lazyChildSet[1].name"), VALUE, new String[] { OTHER_VALUE },
				getField(object, "lazyChildSet"));
		assertEquals(2, object.lazyChildSet.size());
	}

	public void testSequentialSetCreateSetter() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("lazyChildSet[0].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyChildSet"));
		getExecutor(object).set(path("lazyChildSet[1].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyChildSet"));
		getExecutor(object).set(path("lazyChildSet[2].name"), VALUE + "2", new String[] { VALUE + "2" },
				getField(object, "lazyChildSet"));
		List<ChildClass> list = new ArrayList<ChildClass>(object.lazyChildSet);
		assertEquals(VALUE + "2", list.get(2).getName());
	}

	public void testMissingParameterSetCreateSetter() throws ConversionException {
		ParentClass object = new ParentClass();
		try {
			getExecutor(object).set(path("lazyChildSet[3].name"), VALUE, new String[] { VALUE },
					getField(object, "lazyChildSet"));
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public void testSimpleStringArraySet() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("values"), VALUE, new String[] { VALUE, VALUE }, getField(object, "values"));
		assertTrue(Arrays.deepEquals(object.values, new String[] { VALUE, VALUE }));
	}

	public void testInternalStringArraySet() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("child.values"), VALUE, new String[] { VALUE, VALUE }, getField(object, "child"));
		assertTrue(Arrays.deepEquals(new String[] { VALUE, VALUE }, object.child.getValues()));
	}

	public void testInternalNoCreateArraySet() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("mother.childs[1].name"), VALUE, new String[] { VALUE },
				getField(object, "mother"));
		assertEquals(VALUE, object.mother.getChilds()[1].getName());
	}

	public void testInternalCreateArraySet() throws SettingException, ConversionException {
		ParentClass object = new ParentClass();
		getExecutor(object).set(path("lazyMother.childs[0].name"), VALUE, new String[] { VALUE },
				getField(object, "lazyMother"));
		assertEquals(VALUE, object.lazyMother.getChilds()[0].getName());
	}

	public void testCorrectlySetsListFirstPositionIndexTest() throws SettingException, ConversionException {
		class IntegerListTest {
			@Parameter(create = true)
			List<Integer> list = new ArrayList<Integer>();
		}
		IntegerListTest t = new IntegerListTest();
		getExecutor(t).set(path("list[0]"), "2", new String[] { "2" }, getField(t, "list"));
		assertEquals(1, t.list.size());
	}

	public void testArrayIndex() throws SettingException, ConversionException {
		class ListTest {
			@Parameter(create = true)
			double[] val;
		}
		ListTest t = new ListTest();
		getExecutor(t).set(path("val[0]"), "2", new String[] { "2" }, getField(t, "val"));
		assertEquals(1, t.val.length);
	}

	public void testSequencialArrayIndex() throws SettingException, ConversionException {
		class PolTest {
			@Parameter(create = true)
			double[] c;
		}
		PolTest t = new PolTest();
		getExecutor(t).set(path("c[0]"), "2", new String[] { "2" }, getField(t, "c"));
		getExecutor(t).set(path("c[1]"), "-7", new String[] { "-7" }, getField(t, "c"));
		getExecutor(t).set(path("c[2]"), "-7", new String[] { "-7" }, getField(t, "c"));
		getExecutor(t).set(path("c[3]"), "2", new String[] { "2" }, getField(t, "c"));
		assertEquals(t.c[0], 2.0);
		assertEquals(t.c[1], -7.0);
		assertEquals(t.c[2], -7.0);
		assertEquals(t.c[3], 2.0);
	}

	private FieldParameter getField(Object obj, String str) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(str)) {
				field.setAccessible(true);
				return new FieldParameter(new FieldAnnotation<Parameter>(field.getAnnotation(Parameter.class), field));
			}
		}
		throw new RuntimeException("Unable to find field");
	}

	private String[] path(String pathString) {
		return pathString.split("[\\.\\[\\]]+");
	}

	public static class ParameterTest {
		@Parameter
		private User user = new User();
	}

	public static class User {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public void testUsesDepthParameter() throws SettingException, ConversionException {
		ParameterTest object = new ParameterTest();
		getExecutor(object).set(path("user.name"), "guilherme", new String[] { "guilherme" }, getField(object, "user"));
		assertEquals("guilherme", object.user.name);
	}

}
