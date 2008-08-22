package org.vraptor.reflection;

import java.lang.reflect.Array;

import org.vraptor.reflection.ArrayDealer;
import org.vraptor.reflection.SettingException;

import junit.framework.TestCase;


public class ArrayDealerTest extends TestCase {

	private static final String VALUE = "value";

	public void testResizesButIsBiggerThanNeeded() throws SettingException {
		String[] array = createArray(1);
		Object result = new ArrayDealer().resizeAndSet(array, 0, false,
				String.class);
		assertEquals(array, result);
	}

	public void testResizesTooFast() {
		String[] array = createArray(0);
		try {
			new ArrayDealer().resizeAndSet(array, 1, true, String.class);
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public void testResizesIt() throws SettingException {
		String[] array = createArray(0);
		Object result = new ArrayDealer().resizeAndSet(array, 0, true,
				String.class);
		assertEquals(Array.getLength(result), 1);
	}

	public void testResizesItTwiceInARow() throws SettingException {
		String[] array = createArray(0);
		Object result = new ArrayDealer().resizeAndSet(array, 0, true,
				String.class);
		result = new ArrayDealer().resizeAndSet(result, 1, true, String.class);
		assertEquals(Array.getLength(result), 2);
	}

	public void testNotAllowedToCreate() {
		String[] array = createArray(0);
		try {
			new ArrayDealer().resizeAndSet(array, 1, false, String.class);
			fail();
		} catch (SettingException e) {
		}
	}

	private String[] createArray(int i) {
		String[] ar = new String[i];
		for (int j = 0; j < i; j++) {
			ar[j] = j + "/" + i;
		}
		return ar;
	}

	public void testFixedBiggerThanShould() {
		String[] array = createArray(1);
		try {
			new ArrayDealer().resizeAndSet(array, 0, VALUE);
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public void testFixedResizeTooFast() {
		String[] array = createArray(0);
		try {
			new ArrayDealer().resizeAndSet(array, 1, VALUE);
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public void testFixedResizeIt() throws SettingException {
		String[] array = createArray(0);
		Object result = new ArrayDealer().resizeAndSet(array, 0, VALUE);
		assertEquals(Array.getLength(result), 1);
	}

	public void testFixedResizeItTwiceInARow() throws SettingException {
		String[] array = createArray(0);
		Object result = new ArrayDealer().resizeAndSet(array, 0, true,
				String.class);
		result = new ArrayDealer().resizeAndSet(result, 1, VALUE);
		assertEquals(Array.getLength(result), 2);
	}

	public void testNullArray() throws SettingException {
		String[] array = null;
		Object result = new ArrayDealer().resizeAndSet(array, 0, true,
				String.class);
		assertEquals(Array.getLength(result), 1);
	}

	public void testFixedNotAllowedToCreate() {
		String[] array = createArray(0);
		try {
			new ArrayDealer().resizeAndSet(array, 1, VALUE);
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

	public void testTriesToInstantiateWhenForbidden() {
		try {
			new ArrayDealer().resizeAndSet(null, 1, false, String.class);
			fail();
		} catch (SettingException e) {
			// ok
		}
	}

}
