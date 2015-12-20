package es.udc.ws.app.model.facebook;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class FacebookServiceFactory {
	private static FacebookService instance;
	private static final String FACEBOOK_SERVICE_IMPLEMENTATION_PARAM="FacebookImpl";
	private FacebookServiceFactory() {

	}

	@SuppressWarnings("rawtypes")
	private static FacebookService getInstance() {
		try {
			String className=ConfigurationParametersManager
                    .getParameter(FACEBOOK_SERVICE_IMPLEMENTATION_PARAM);
			Class service = Class.forName(className);
			return (FacebookService) service.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized static FacebookService instance() {
		if (instance == null)
			instance = getInstance();
		return instance;
	}
}
