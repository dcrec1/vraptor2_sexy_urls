package org.vraptor.remote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.vraptor.remote.json.JSONSerializer;

public class JSONSerializerTest extends TestCase {

	public static class Person {
		private String name = "Nico";

		private int age = 1;

		public int getAge() {
			return age;
		}

		public String getName() {
			return name;
		}

	}

	public void testSimpleBeanPerson() {
		JSONSerializer ser = new JSONSerializer();
		assertEquals("{\"age\":1,\"name\":\"Nico\"}", ser.serialize(new Person()).toString());
	}

	public void testNull() {
		JSONSerializer ser = new JSONSerializer();
		assertEquals("null", ser.serialize(null).toString());
	}

	public void testBoolean() {
		JSONSerializer ser = new JSONSerializer();
		assertEquals("false", ser.serialize(false).toString());
		assertEquals("true", ser.serialize(true).toString());
		assertEquals("true", ser.serialize(Boolean.TRUE).toString());
	}

	public void testDate() throws ParseException {
		Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("14/05/1977 20:33");
		JSONSerializer ser = new JSONSerializer();
		assertEquals("" + date.getTime(), ser.serialize(date).toString());
	}

	public void testCalendar() throws ParseException {
		Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("14/05/1977 20:33");
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(date);
		JSONSerializer ser = new JSONSerializer();
		assertEquals("" + c.getTimeInMillis(), ser.serialize(c).toString());
	}

	
	public void testScape() {
		JSONSerializer ser = new JSONSerializer();
		assertEquals("\"\\\"\\\\\\/\"", ser.serialize("\"\\/").toString());
	}

	public void testLineFeed() {
		JSONSerializer ser = new JSONSerializer();
		assertEquals("\"\\r\\n\"", ser.serialize("\r\n").toString());
	}

	public void testSimpleBeanPersonList() {
		JSONSerializer ser = new JSONSerializer();
		List<Person> list = new ArrayList<Person>();
		list.add(new Person());
		list.add(new Person());
		assertEquals("[{\"age\":1,\"name\":\"Nico\"},{\"age\":1,\"name\":\"Nico\"}]", ser.serialize(list).toString());

	}

	public void testSerialize1String() {
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("string", "name");
		JSONSerializer ser = new JSONSerializer();
		assertEquals("{\"string\":\"name\"}", ser.serialize(objects).toString());
	}

	public void testSerializeUnicodeString() {
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("string", "\u5B66\uD800\uDF30");
		JSONSerializer ser = new JSONSerializer();
		assertEquals("{\"string\":\"\u5B66\uD800\uDF30\"}", ser.serialize(objects).toString());
	}

	public void testSerialize1Bean() {
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("bean", new Bean());
		JSONSerializer ser = new JSONSerializer();
		assertEquals("{\"bean\":{\"first\":1,\"second\":3.2}}", ser.serialize(objects).toString());
		assertTrue((ser.serialize(objects).toString().contains("\"first\":1")));
	}

	public void testSerializeBeanInsideBean() {
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("bean2", new Bean2());
		JSONSerializer ser = new JSONSerializer();
		assertEquals("{\"bean2\":{\"bean\":{\"first\":1,\"second\":3.2}}}", ser.serialize(objects).toString());
	}

	public void testSerializeBeanInsideBeanTestingDepth() {
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("bean2", new Bean2());
		JSONSerializer ser = new JSONSerializer(2);
		assertEquals("{\"bean2\":{\"bean\":{\"first\":{},\"second\":{}}}}", ser.serialize(objects).toString());
	}

	public void testSerializeBoolean() {
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("boolean", true);
		JSONSerializer ser = new JSONSerializer();
		assertTrue((ser.serialize(objects).toString().contains("\"boolean\":true")));
	}

	public void testSerializeMapInsideMap() {
		Map<String, Object> objects = new HashMap<String, Object>();
		Map<String, Object> otherMap = new HashMap<String, Object>();
		otherMap.put("age", 5);
		objects.put("map", otherMap);
		JSONSerializer ser = new JSONSerializer();
		assertTrue((ser.serialize(objects).toString().contains("{\"map\":{\"age\":5}}")));
	}

	public void testSerializeBizarreBean() {
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("bean", new BizarreBean());
		JSONSerializer ser = new JSONSerializer();
		assertEquals("{\"bean\":{}}", ser.serialize(objects).toString());
	}

	public void testSerializeSet() {
		Map<String, Object> objects = new HashMap<String, Object>();
		Set<Object> set = new LinkedHashSet<Object>();
		set.add(3.7);
		set.add(-4);
		objects.put("set", set);
		JSONSerializer ser = new JSONSerializer();
		assertTrue((ser.serialize(objects).toString().contains("{\"set\":[3.7,-4]}")));
	}

	public void testSerializeList() {
		Map<String, Object> objects = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		list.add(3.7);
		list.add(-4);
		objects.put("list", list);
		JSONSerializer ser = new JSONSerializer();
		assertTrue((ser.serialize(objects).toString().contains("{\"list\":[3.7,-4]}")));
	}

	public void testSerializeArray() {
		Map<String, Object> objects = new HashMap<String, Object>();
		int[] array = { 7, 4, 12 };
		objects.put("array", array);
		JSONSerializer ser = new JSONSerializer();
		assertTrue((ser.serialize(objects).toString().contains("{\"array\":[7,4,12]}")));
	}

	public void testSerializeArray2() {
		Map<String, Object> objects = new HashMap<String, Object>();
		Integer[] array = { 7, 4, 12 };
		objects.put("array", array);
		JSONSerializer ser = new JSONSerializer();
		assertTrue((ser.serialize(objects).toString().contains("{\"array\":[7,4,12]}")));
	}

	public void testSerializeCyclicDependecy() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map.put("map1", map1);
			map1.put("map", map);
			JSONSerializer ser = new JSONSerializer();
			ser.serialize(map);
			// ok
		} catch (StackOverflowError ex) {
			fail();
		}
	}

	public void testMaximumDepth() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		map.put("map1", map1);
		map1.put("map2", map2);
		map2.put("map3", map3);
		map3.put("last", "one");
		JSONSerializer ser = new JSONSerializer(3);
		assertFalse(ser.serialize(map).toString().contains("last"));
		assertFalse(ser.serialize(map).toString().contains("one"));
	}

	public static class Bean2 {
		private Bean bean = new Bean();

		public Bean getBean() {
			return bean;
		}
	}

	public static class Bean {
		private int first = 1;

		private double second = 3.2;

		public int getFirst() {
			return first;
		}

		public double getSecond() {
			return second;
		}
	}

	public static class BizarreBean {
		private int first = 1;

		private double second = 3.2;

		public int getFirst(double d) {
			return first;
		}

		public double get() {
			return second;
		}
	}

}
