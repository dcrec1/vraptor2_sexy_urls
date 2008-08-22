package org.vraptor.scope;

import org.vraptor.LogicRequest;

/**
 * All scope types for injection and outjection.
 *
 * @author Guilherme Silveira
 * @author Paulo Silveira
 */
public enum ScopeType {
	/**
	 * Uses variables in the HttpServletRequest attributes, and also in the
	 * parameters, if not found as attribute.
	 */
	REQUEST {
		public Context getContext(LogicRequest request) {
			return request.getRequestContext();
		}
	},
	/**
	 * Uses variables in the HttpSession
	 */
	SESSION {
		public Context getContext(LogicRequest request) {
			return request.getSessionContext();
		}

	}

	,
	/**
	 * Use variables in the servletContext.
	 */
	APPLICATION {
		public Context getContext(LogicRequest request) {
			return request.getApplicationContext();
		}

	}
    
    ,
    FLASH {
        public Context getContext(LogicRequest request) {
            return request.getFlashContext();
        }
        
    };

	/**
	 * Returns the repective context for the given ScopeType
	 *
	 * @param request
	 * @return
	 */
	public abstract Context getContext(LogicRequest request);
}
