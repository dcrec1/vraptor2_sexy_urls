package org.vraptor.view;

import junit.framework.TestCase;


public class RegexViewManagerTest extends TestCase{
	
	private RegexViewManager viewManager = new RegexViewManager("");
	
	public void testTranslateExpressionOfComponent() {
		String result = viewManager.translateExpression("/$component/$2.$3.jsp");
		assertTrue("/$1/$2.$3.jsp".equals(result));
	}
	
	public void testTranslateExpressionOfLogic() {
		String result = viewManager.translateExpression("/$1/$logic.$3.jsp");
		assertTrue("/$1/$2.$3.jsp".equals(result));
	}
	
	public void testTranslateExpressionOfResult() {
		String result = viewManager.translateExpression("/$1/$2.$result.jsp");
		assertTrue("/$1/$2.$3.jsp".equals(result));
	}
	
	public void testTranslateExpressionWithNullValue() {
		String result = viewManager.translateExpression(null);
		assertTrue(result == null);
	}
	
	public void testTranslateExpressionWithEmptyString() {
		String result = viewManager.translateExpression("");
		assertTrue(result == "");
	}
	
	public void testTranslateExpressionWithAndWithoutDollarSign() {
		String result = viewManager.translateExpression("component$componentlogic$logicresult$result");
		assertTrue("component$1logic$2result$3".equals(result));
	}
}
