package org.vraptor.introspector;

import java.util.TreeSet;

import junit.framework.TestCase;

public class ParameterTest extends TestCase {

	public void testEquals() {
		Parameter p = new Parameter("a.b.c.d.e[0].f");
		assertEquals(0, p.compareTo(p));
	}

	public void testComparesSizeDifferenceWithArrayInTheEnd() {
		Parameter p = new Parameter("a.b.c.d.e[0]");
		Parameter p2 = new Parameter("a.b.c.d.e[0].f");
		assertTrue(p.compareTo(p2) < 0);
	}

	public void testComparesSizeDifference() {
		Parameter p = new Parameter("a.b.c.d");
		Parameter p2 = new Parameter("a.b.c.d.e");
		assertTrue(p.compareTo(p2) < 0);
	}

	public void testComparesDifferentArrayPositions() {
		Parameter p = new Parameter("a.b.c[1].d");
		Parameter p2 = new Parameter("a.b.c[0].d");
		assertTrue(p.compareTo(p2) > 0);
	}

	public void testComparesDifferentArrayPositionsInTheEnd() {
		Parameter p = new Parameter("a.b.c[1]");
		Parameter p2 = new Parameter("a.b.c[5]");
		assertTrue(p.compareTo(p2) < 0);
	}

	public void testComparesInvalidString() {
		Parameter p = new Parameter("a.b.c[0]");
		Parameter p2 = new Parameter("a.b.c.d");
		assertTrue(p.compareTo(p2) > 0);
	}

	public void testComparesDifferentStrings() {
		Parameter p = new Parameter("a");
		Parameter p2 = new Parameter("b");
		assertTrue(p.compareTo(p2) < 0);
	}

	public void testComparesDifferentStringsInATreeSet() {
		Parameter p = new Parameter("a");
		Parameter p2 = new Parameter("b");
		TreeSet<Parameter> ts = new TreeSet<Parameter>();
		ts.add(p);
		ts.add(p2);
		assert(ts.first().equals(p));
	}

}
