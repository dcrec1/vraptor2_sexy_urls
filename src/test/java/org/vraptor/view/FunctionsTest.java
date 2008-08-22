package org.vraptor.view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

public class FunctionsTest extends TestCase {

	public void testCallsGetter() {
		SimpleGetter sg = new SimpleGetter();
		sg.i = 15;
		Object property = Functions.getProperty(sg, "i");
		assertEquals(sg.i, property);
	}

	static class SimpleGetter {
		Integer i;

		public Integer getI() {
			return i;
		}
	}

	public void testConvertsNewLinesToBr() {
		assertEquals("<br />\n", Functions.stringToHtml("\n"));
	}

	public void testHandlesNullList() {
		assertNull(Functions.setToList(null));
	}

	public void testHandlesListsWithItems() {
		Set<Object> set = new HashSet<Object>();
		for (int i = 0; i < 10; i++) {
			set.add(new Object());
		}
		List<Object> list = Functions.setToList(set);
		assertTrue(list.containsAll(set));
		assertTrue(set.containsAll(list));
	}

}
