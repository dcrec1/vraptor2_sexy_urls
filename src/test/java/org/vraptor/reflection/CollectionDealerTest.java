package org.vraptor.reflection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.vraptor.reflection.CollectionDealer;
import org.vraptor.reflection.SettingException;

import junit.framework.TestCase;


public class CollectionDealerTest extends TestCase {

	public void testArrayListExactSize() throws SettingException {
		ArrayList<String> array = createArrayList(1);
		Object result = new CollectionDealer().resizeAndSet(array, 0, false,
				String.class);
		assertEquals(array.size(), 1);
		assertEquals(array, result);
	}

	public void testArrayListBiggerThanNeeded() throws SettingException {
		ArrayList<String> array = createArrayList(3);
		Object result = new CollectionDealer().resizeAndSet(array, 0, false,
				String.class);
		assertEquals(array.size(), 3);
		assertEquals(array, result);
	}

	public void testArrayListResizeIt() throws SettingException {
		ArrayList<String> array = createArrayList(0);
		ArrayList result = (ArrayList) new CollectionDealer().resizeAndSet(
				array, 0, true, String.class);
		assertEquals(array.size(), 1);
		assertEquals(array, result);
	}

	public void testArrayListResizeTooMuch() {
		ArrayList<String> array = createArrayList(0);
		try {
			new CollectionDealer().resizeAndSet(array, 1, true, String.class);
			fail();
		} catch (SettingException e) {
		}
	}

	public void testArrayListNotAllowedToCreate() throws SettingException {
		try {
			ArrayList<String> array = createArrayList(0);
			new CollectionDealer().resizeAndSet(array, 1, false, String.class);
			fail("Should be unable to create objects");
		} catch (SettingException e) {
		}
	}

	private ArrayList<String> createArrayList(int i) {
		ArrayList<String> ar = new ArrayList<String>();
		for (int j = 0; j < i; j++) {
			ar.add(j + "/" + i);
		}
		return ar;
	}

	public void testSetExactSize() throws SettingException {
		Set<String> array = createSet(1);
		Object result = new CollectionDealer().resizeAndSet(array, 0, false,
				String.class);
		assertEquals(array.size(), 1);
		assertEquals(array, result);
	}

	public void testSetBiggerThanNeeded() throws SettingException {
		Set<String> array = createSet(3);
		Object result = new CollectionDealer().resizeAndSet(array, 0, false,
				String.class);
		assertEquals(array.size(), 3);
		assertEquals(array, result);
	}

	public void testSetResizeIt() throws SettingException {
		Set<String> array = createSet(0);
		Set result = (Set) new CollectionDealer().resizeAndSet(array, 0, true,
				String.class);
		assertEquals(array.size(), 1);
		assertEquals(array, result);
	}

	public void testSetNotAllowedToCreate() {
		try {
			Set<String> array = createSet(0);
			new CollectionDealer().resizeAndSet(array, 1, false, String.class);
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	private Set<String> createSet(int i) {
		Set<String> ar = new HashSet<String>();
		for (int j = 0; j < i; j++) {
			ar.add(j + "/" + i);
		}
		return ar;
	}

}
