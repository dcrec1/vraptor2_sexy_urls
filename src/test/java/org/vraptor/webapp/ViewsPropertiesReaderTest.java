package org.vraptor.webapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.vraptor.AbstractTest;
import org.vraptor.view.DummyViewManager;
import org.vraptor.view.ViewException;
import org.vraptor.view.ViewManager;

public class ViewsPropertiesReaderTest extends AbstractTest {

	private InputStream getStream() {
		String s = "overriden.logic.result = 1\n" + "# not_overriden.logic.result = 0";
		return new ByteArrayInputStream(s.getBytes());
	}

	public void testLeavesUnchangedEntriesAsTheyWere() throws IOException, ViewException {
		ViewsPropertiesReader reader = new ViewsPropertiesReader();
		DummyViewManager dummy = new DummyViewManager();
		ViewManager manager = reader.overrideViews(dummy, getStream());
		manager.forward(createLogicRequest("not_overriden", "logic"), "result");
		assertEquals("forward::result", dummy.getResult());
	}

	public void testGrabsOverridenEntries() throws IOException, ViewException {
		ViewsPropertiesReader reader = new ViewsPropertiesReader();
		DummyViewManager dummy = new DummyViewManager();
		ViewManager manager = reader.overrideViews(dummy, getStream());
		manager.forward(createLogicRequest("overriden", "logic"), "result");
		assertEquals("directForward::result", dummy.getResult());
	}

}
