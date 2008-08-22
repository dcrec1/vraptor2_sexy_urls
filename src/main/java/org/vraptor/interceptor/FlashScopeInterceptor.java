package org.vraptor.interceptor;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.scope.FlashContext;
import org.vraptor.view.ViewException;

/**
 * The flash scope interceptor is responsible for managing the flash context
 * within vraptor.
 * 
 * @author Guilherme Silveira
 * @since 2.4
 */
public class FlashScopeInterceptor implements Interceptor {

    public void intercept(LogicFlow flow) throws LogicException, ViewException {
        FlashContext current = flow.getLogicRequest().getFlashContext();
        current.dumpToRequest();
        current.renew();
        flow.execute();
    }

}
