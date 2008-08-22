package org.vraptor.component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

public class ClazzTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testInstantiatesASingleObject() throws ComponentInstantiationException {
		Clazz type = new Clazz(Object.class);
		Class instanceType = type.newInstance().getClass();
		assertEquals(Object.class, instanceType);
	}

	@SuppressWarnings("unchecked")
	public void testInstantiatesASingleString() throws ComponentInstantiationException {
		Clazz type = new Clazz(String.class);
		Class instanceType = type.newInstance().getClass();
		assertEquals(String.class, instanceType);
	}

	@SuppressWarnings("unchecked")
	public void testInstantiatesAList() throws ComponentInstantiationException {
		Clazz type = new Clazz(List.class);
		Class instanceType = type.newInstance().getClass();
		assertEquals(ArrayList.class, instanceType);
	}

	@SuppressWarnings("unchecked")
	public void testInstantiatesASet() throws ComponentInstantiationException {
		Clazz type = new Clazz(Set.class);
		Class instanceType = type.newInstance().getClass();
		assertEquals(LinkedHashSet.class, instanceType);
	}

	@SuppressWarnings("unchecked")
	public void testInstantiatesACollection() throws ComponentInstantiationException {
		Clazz type = new Clazz(ArrayList.class);
		Class instanceType = type.newInstance().getClass();
		assertEquals(ArrayList.class, instanceType);
	}

	@SuppressWarnings("unchecked")
	public void testInstantiatesAMap() throws ComponentInstantiationException {
		Clazz type = new Clazz(HashMap.class);
		Class instanceType = type.newInstance().getClass();
		assertEquals(HashMap.class, instanceType);
	}

	public void testInstantiatesFloatPrimitiveTypes() throws ComponentInstantiationException {
		testInstantiatesPrimitive(float.class, Float.class);
	}

	public void testInstantiatesDoublePrimitiveTypes() throws ComponentInstantiationException {
		testInstantiatesPrimitive(double.class, Double.class);
	}

	public void testInstantiatesIntegerPrimitiveTypes() throws ComponentInstantiationException {
		testInstantiatesPrimitive(int.class, Integer.class);
	}

	public void testInstantiatesLongPrimitiveTypes() throws ComponentInstantiationException {
		testInstantiatesPrimitive(long.class, Long.class);
	}

	public void testInstantiatesShortPrimitiveTypes() throws ComponentInstantiationException {
		testInstantiatesPrimitive(short.class, Short.class);
	}

	public void testInstantiatesBytePrimitiveTypes() throws ComponentInstantiationException {
		testInstantiatesPrimitive(byte.class, Byte.class);
	}

	public void testInstantiatesBooleanPrimitiveTypes() throws ComponentInstantiationException {
		testInstantiatesPrimitive(boolean.class, Boolean.class);
	}

	public void testInstantiatesCharPrimitiveTypes() throws ComponentInstantiationException {
		testInstantiatesPrimitive(char.class, Character.class);
	}

	@SuppressWarnings("unchecked")
	private void testInstantiatesPrimitive(Class genericType, Class expected) throws ComponentInstantiationException {
		Clazz type = new Clazz(genericType);
		Object value = type.newInstance();
		Class instanceType = value.getClass();
		assertEquals(expected, instanceType);
	}

	@SuppressWarnings("unchecked")
	public void testInstantiatesACalendar() throws ComponentInstantiationException {
		Clazz type = new Clazz(Calendar.class);
		Class instanceType = type.newInstance().getClass();
		assertEquals(GregorianCalendar.class, instanceType);
	}

}
