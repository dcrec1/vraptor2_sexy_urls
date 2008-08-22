package org.vraptor.i18n;

import java.util.Locale;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.test.MockedServletContext;

/**
 * @author Fabio Kung
 * 
 */
public class JstlWrapperTest extends AbstractTest {
	public static final String JSTL_LOCALE_KEY = "javax.servlet.jsp.jstl.fmt.locale";

	public static final String JSTL_FALLBACK_LOCALE_KEY = "javax.servlet.jsp.jstl.fmt.fallbackLocale";

	public void testFindNonExistentAttribute() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getRequestContext().setAttribute("my.attribute.request", "someValue");
		Object value = jstlWrapper.find(context, "my.nonexistent.attribute");
		assertNull(value);
	}

	public void testFindExistentAttributeInRequestContext() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		String attributeValue = "myValue";
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getRequestContext().setAttribute("my.attribute.request", attributeValue);
		Object value = jstlWrapper.find(context, "my.attribute");
		assertSame(attributeValue, value);
	}

	public void testFindExistentAttributeInSessionContext() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		String attributeValue = "myValue";
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getSessionContext().setAttribute("my.attribute.session", attributeValue);
		Object value = jstlWrapper.find(context, "my.attribute");
		assertSame(attributeValue, value);
	}

	public void testFindExistentAttributeInApplicationContext() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		String attributeValue = "myValue";
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getApplicationContext().setAttribute("my.attribute.application", attributeValue);
		Object value = jstlWrapper.find(context, "my.attribute");
		assertSame(attributeValue, value);
	}

	public void testFindExistentAttributeAsInitParameter() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		String attributeValue = "myValue";
		LogicRequest context = (LogicRequest) createLogicRequest();
		MockedServletContext servletContext = (MockedServletContext) context.getRequest().getSession().getServletContext();
		servletContext.setInitParameter("my.attribute", attributeValue);
		Object value = jstlWrapper.find(context, "my.attribute");
		assertSame(attributeValue, value);
	}

	public void testFindShouldUseTheSmallestContext() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getRequestContext().setAttribute("my.attribute.request", "valueInRequest");
		context.getSessionContext().setAttribute("my.attribute.session", "valueInSession");
		context.getApplicationContext().setAttribute("my.attribute.application", "valueInApplication");
		MockedServletContext servletContext = (MockedServletContext) context.getRequest().getSession().getServletContext();
		servletContext.setInitParameter("my.attribute", "valueAsInitParameter");
		Object value = jstlWrapper.find(context, "my.attribute");
		assertEquals("All contexts filled", "valueInRequest", value);

		context = (LogicRequest) createLogicRequest();
		context.getSessionContext().setAttribute("my.attribute.session", "valueInSession");
		context.getApplicationContext().setAttribute("my.attribute.application", "valueInApplication");
		value = jstlWrapper.find(context, "my.attribute");
		assertEquals("Session and Application contexts filled", "valueInSession", value);
	}

	public void testFindLocaleProvidedByTheClient() {
		// there aren't locales in any context
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		Locale expected = context.getRequest().getLocale();
		assertNotNull(expected);

		// get the Locale sent by the client (on the request, browser
		// configuration)
		Locale locale = jstlWrapper.findLocale(context);
		assertNotNull(locale);
		assertEquals(expected, locale);
	}

	public void testFindLocaleInRequest() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getRequestContext().setAttribute(JSTL_LOCALE_KEY + ".request", Locale.GERMANY);
		Locale locale = jstlWrapper.findLocale(context);
		assertEquals(Locale.GERMANY, locale);
	}

	public void testFindLocaleInSession() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getSessionContext().setAttribute(JSTL_LOCALE_KEY + ".session", Locale.GERMANY);
		Locale locale = jstlWrapper.findLocale(context);
		assertEquals(Locale.GERMANY, locale);
	}

	public void testFindLocaleInApplication() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getApplicationContext().setAttribute(JSTL_LOCALE_KEY + ".application", Locale.GERMANY);
		Locale locale = jstlWrapper.findLocale(context);
		assertEquals(Locale.GERMANY, locale);
	}

	public void testFindLocaleAsInitParameter() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		MockedServletContext servletContext = (MockedServletContext) context.getRequest().getSession().getServletContext();
		servletContext.setInitParameter(JSTL_LOCALE_KEY, "de_DE");
		Locale locale = jstlWrapper.findLocale(context);
		assertEquals(Locale.GERMANY, locale);
	}

	public void testFindLocaleShouldUseTheSmallestContext() {
		// all filled
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getRequestContext().setAttribute(JSTL_LOCALE_KEY + ".request", Locale.GERMANY);
		context.getSessionContext().setAttribute(JSTL_LOCALE_KEY + ".session", Locale.FRANCE);
		context.getApplicationContext().setAttribute(JSTL_LOCALE_KEY + ".application", Locale.ITALY);
		MockedServletContext servletContext = (MockedServletContext) context.getRequest().getSession().getServletContext();
		servletContext.setInitParameter(JSTL_LOCALE_KEY, "en_GB");
		Locale locale = jstlWrapper.findLocale(context);
		assertEquals(Locale.GERMANY, locale);

		// all but request
		context = (LogicRequest) createLogicRequest();
		context.getSessionContext().setAttribute(JSTL_LOCALE_KEY + ".session", Locale.FRANCE);
		context.getApplicationContext().setAttribute(JSTL_LOCALE_KEY + ".application", Locale.ITALY);
		servletContext = (MockedServletContext) context.getRequest().getSession().getServletContext();
		servletContext.setInitParameter(JSTL_LOCALE_KEY, "en_GB");
		locale = jstlWrapper.findLocale(context);
		assertEquals(Locale.FRANCE, locale);
		
		// application and init parameter
		context = (LogicRequest) createLogicRequest();
		context.getApplicationContext().setAttribute(JSTL_LOCALE_KEY + ".application", Locale.ITALY);
		servletContext = (MockedServletContext) context.getRequest().getSession().getServletContext();
		servletContext.setInitParameter(JSTL_LOCALE_KEY, "en_GB");
		locale = jstlWrapper.findLocale(context);
		assertEquals(Locale.ITALY, locale);
	}

	public void testFindFallbackLocaleProvidedByTheClient() {
		// there aren't locales in any context
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		Locale expected = context.getRequest().getLocale();
		assertNotNull(expected);

		// get the Locale sent by the client (on the request, browser
		// configuration)
		Locale locale = jstlWrapper.findFallbackLocale(context);
		assertNotNull(locale);
		assertEquals(expected, locale);
	}

	public void testFindFallbackLocaleInRequest() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getRequestContext().setAttribute(JSTL_FALLBACK_LOCALE_KEY + ".request", Locale.GERMANY);
		Locale locale = jstlWrapper.findFallbackLocale(context);
		assertEquals(Locale.GERMANY, locale);
	}

	public void testFindFallbackLocaleInSession() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getSessionContext().setAttribute(JSTL_FALLBACK_LOCALE_KEY + ".session", Locale.GERMANY);
		Locale locale = jstlWrapper.findFallbackLocale(context);
		assertEquals(Locale.GERMANY, locale);
	}

	public void testFindFallbackLocaleInApplication() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getApplicationContext().setAttribute(JSTL_FALLBACK_LOCALE_KEY + ".application", Locale.GERMANY);
		Locale locale = jstlWrapper.findFallbackLocale(context);
		assertEquals(Locale.GERMANY, locale);
	}

	public void testFindFallbackLocaleAsInitParameter() {
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		MockedServletContext servletContext = (MockedServletContext) context.getRequest().getSession().getServletContext();
		servletContext.setInitParameter(JSTL_FALLBACK_LOCALE_KEY, "de_DE");
		Locale locale = jstlWrapper.findFallbackLocale(context);
		assertEquals(Locale.GERMANY, locale);
	}

	public void testFindFallbackLocaleShouldUseTheSmallestContext() {
		// all filled
		JstlWrapper jstlWrapper = new JstlWrapper();
		LogicRequest context = (LogicRequest) createLogicRequest();
		context.getRequestContext().setAttribute(JSTL_FALLBACK_LOCALE_KEY + ".request", Locale.GERMANY);
		context.getSessionContext().setAttribute(JSTL_FALLBACK_LOCALE_KEY + ".session", Locale.FRANCE);
		context.getApplicationContext().setAttribute(JSTL_FALLBACK_LOCALE_KEY + ".application", Locale.ITALY);
		MockedServletContext servletContext = (MockedServletContext) context.getRequest().getSession().getServletContext();
		servletContext.setInitParameter(JSTL_FALLBACK_LOCALE_KEY, "en_GB");
		Locale locale = jstlWrapper.findFallbackLocale(context);
		assertEquals(Locale.GERMANY, locale);
		
		// all but request
		context = (LogicRequest) createLogicRequest();
		context.getSessionContext().setAttribute(JSTL_FALLBACK_LOCALE_KEY + ".session", Locale.FRANCE);
		context.getApplicationContext().setAttribute(JSTL_FALLBACK_LOCALE_KEY + ".application", Locale.ITALY);
		servletContext = (MockedServletContext) context.getRequest().getSession().getServletContext();
		servletContext.setInitParameter(JSTL_FALLBACK_LOCALE_KEY, "en_GB");
		locale = jstlWrapper.findFallbackLocale(context);
		assertEquals(Locale.FRANCE, locale);

		// application and init parameter
		context = (LogicRequest) createLogicRequest();
		context.getApplicationContext().setAttribute(JSTL_FALLBACK_LOCALE_KEY + ".application", Locale.ITALY);
		servletContext = (MockedServletContext) context.getRequest().getSession().getServletContext();
		servletContext.setInitParameter(JSTL_FALLBACK_LOCALE_KEY, "en_GB");
		locale = jstlWrapper.findFallbackLocale(context);
		assertEquals(Locale.ITALY, locale);
	}

	public void testStringToLocale() {
		// TODO implement
		// Maybe it's already tested on xstream
	}
}
