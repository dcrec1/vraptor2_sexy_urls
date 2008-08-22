package org.vraptor.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.Remotable;
import org.vraptor.component.ComponentType;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.scope.ScopeType;

/**
 * If it is a remote vie request, like JSON or XML request, render it. If not,
 * delegates to the internal manager.
 * @author Paulo Silveira
 * @version 2.2.1
 *
 */
public class RemoteViewManager implements ViewManager {

	private static final Logger LOG = Logger.getLogger(RemoteViewManager.class);

	private static final String UTF8 = "UTF-8";

	private final ViewManager internalManager;

	private final RemoteView remote;

	public RemoteViewManager(ViewManager viewManager, RemoteView view) {
		this.internalManager = viewManager;
		this.remote = view;
	}

	public void directForward(LogicRequest logicRequest, String result, String forwardUrl) throws ViewException {
		internalManager.directForward(logicRequest, result, forwardUrl);
	}

	public void forward(LogicRequest logicRequest, String result) throws ViewException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("delegating redering to remote view " + remote);
		}
		Object comp = logicRequest.getLogicDefinition().getComponent();
		ComponentType type = logicRequest.getLogicDefinition().getComponentType();

		try {
			Map<String, Object> outjected = getOutjectedValuesFromAnyScope(type, comp);
			Remotable remotable = logicRequest.getLogicDefinition().getLogicMethod().getMetadata().getAnnotation(Remotable.class);
			CharSequence output = remote.newSerializer(remotable.depth()).serialize(outjected);

			logicRequest.getResponse().setCharacterEncoding(UTF8);
			logicRequest.getResponse().setContentType(remote.getContentType());

			PrintWriter writer = logicRequest.getResponse().getWriter();
			writer.append(output);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new ViewException(e);
		} catch (GettingException e) {
			throw new ViewException(e);
		} catch (MethodInvocationException e) {
			throw new ViewException(e);
		}
		
	}

	private Map<String, Object> getOutjectedValuesFromAnyScope(ComponentType type,  Object comp) throws GettingException, MethodInvocationException {

		Map<String, Object> outjected = new HashMap<String, Object>();

		for(ScopeType scope : ScopeType.values()) {
			outjected.putAll(type.getOutjectedValues(comp, scope));
		}

		return outjected;
	}

	public void redirect(LogicRequest request, String result) throws ViewException {
		internalManager.redirect(request, result);
	}

}

