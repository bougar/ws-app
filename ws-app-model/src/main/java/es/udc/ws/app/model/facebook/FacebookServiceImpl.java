package es.udc.ws.app.model.facebook;



import org.apache.http.HttpResponse;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.util.configuration.ConfigurationParametersManager;


public class FacebookServiceImpl implements FacebookService {
	private static final String FACEBOOK_PAGE_ID_PARAM = "FacebookId";
	private static final String FACEBOOK_TOKEN_PARAM = "FacebookToken";
	private static final String FACEBOOK_API_PARAM = "FacebookApi";
	private final String facebookApi;
	private final String facebookToken;
	private final String facebookPageId;

	public FacebookServiceImpl() {
		String id = null;
		String token = null;
		String api = null;
		try {
			id = ConfigurationParametersManager
					.getParameter(FACEBOOK_PAGE_ID_PARAM);
			token = ConfigurationParametersManager
					.getParameter(FACEBOOK_TOKEN_PARAM);
			api = ConfigurationParametersManager
					.getParameter(FACEBOOK_API_PARAM);
			if (id == null || token == null || api == null)
				throw new Exception("bad parameters");
		} catch (Exception e) {
			id = null;
			token = null;
			api = null;
			System.err.println("Properties settings are misconfigured");
		}
		facebookApi = api;
		facebookPageId = id;
		facebookToken = token;
	}

	@Override
	public String addOffer(Offer o) throws Exception {
		try {
			HttpResponse response = Request
					.Post(facebookApi + facebookPageId + "/" + "feed")
					.bodyForm(
							Form.form().add("access_token", facebookToken)
									.add("message", o.toString()).build())
					.execute().returnResponse();
			validateStatusCode(200, response);
			return FacebookParser.toStringKey(
					response.getEntity().getContent(), "id");
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void removeOffer(String facebookOfferId) throws Exception {
		try {
			HttpResponse response = Request
					.Delete(facebookApi + facebookOfferId).execute()
					.returnResponse();
			validateStatusCode(200, response);
		} catch (Exception e) {
			throw e;
		}
	}

	public String updateOffer(String facebookOfferId, Offer o) throws Exception {
		
			HttpResponse response = Request
					.Delete(facebookApi + facebookOfferId).execute()
					.returnResponse();
			validateStatusCode(200, response);
			response = Request
					.Post(facebookApi + facebookPageId + "/" + "feed")
					.bodyForm(
							Form.form().add("access_token", facebookToken)
									.add("message", o.toString()).build())
					.execute().returnResponse();
			validateStatusCode(200, response);
		try {
			return FacebookParser.toStringKey(
					response.getEntity().getContent(), "id");
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Long getOfferLikes(String facebookOfferId) {
		Long likes = null;
		try {
			HttpResponse response = Request
					.Get(facebookApi + facebookOfferId + "/likes").execute()
					.returnResponse();
			likes = FacebookParser.getArraySizeKey(response.getEntity()
					.getContent(), "data");
		} catch (Exception e) {
			System.err.println("Error getting offer likes from facebook");
		}
		return likes;
	}

	private void validateStatusCode(int successCode, HttpResponse response)
			throws FacebookException {

		try {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == successCode)
				return;
			throw new FacebookException(statusCode,);
		} catch (Exception e) {
			throw e;
		}

	}
	
	private String parseFacebookError(){
		
	}
}
