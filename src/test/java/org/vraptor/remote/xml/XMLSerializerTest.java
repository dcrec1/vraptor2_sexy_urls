package org.vraptor.remote.xml;

import junit.framework.TestCase;

public class XMLSerializerTest extends TestCase {

	public void testStripsPackageNameForNonAliasedType() {
		// TODO failing?
		MyObject type = new MyObject();
		String expected = "<MyObject/>";
		assertEquals(expected, new XMLSerializer().serialize(type));
	}

	public void testDoesntStripPackageNameForWrapperTypes() {
		// TODO failing?
		Number numbers[] = new Number[] { new Byte((byte) 0), new Short((short) 0), new Integer(0), new Long(0),
				new Float(0), new Double(0) };
		String expected = "<number-array>\n" +
							"  <byte>0</byte>\n" +
							"  <short>0</short>\n" +
							"  <int>0</int>\n" +
							"  <long>0</long>\n" +
							"  <float>0.0</float>\n" +
							"  <double>0.0</double>\n" +
							"</number-array>";
		assertEquals(expected, new XMLSerializer().serialize(numbers));
	}

}
