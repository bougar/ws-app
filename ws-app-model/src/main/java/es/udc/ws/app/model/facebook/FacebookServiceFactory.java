package es.udc.ws.app.model.facebook;

public class FacebookServiceFactory {
	private static FacebookService instance;

	private FacebookServiceFactory() {

	}

	@SuppressWarnings("rawtypes")
	private static FacebookService getInstance() {
		try {
			Class service = Class.forName("FacebookServiceImpl");
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
