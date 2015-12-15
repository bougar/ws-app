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

	/*Class contructor*/
	public FacebookServiceImpl() throws FacebookException {
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
			facebookApi = null;
			facebookPageId = null;
			facebookToken = null;
			throw new FacebookException(e);
		}
		facebookApi = api;
		facebookPageId = id;
		facebookToken = token;
	}

	@Override
	public String addOffer(Offer o) throws HttpFacebookException,
			FacebookException {
		try {
			HttpResponse response = Request
					.Post(facebookApi + facebookPageId + "/" + "feed")
					.bodyForm(
							Form.form().add("access_token", facebookToken)
									.add("message", o.toString()).build())
					.execute().returnResponse();
			validateStatusCode(200, response, "addOffer: ");
			return FacebookParser.toStringKey(
					response.getEntity().getContent(), "id");
		} catch (HttpFacebookException e) {
			throw e;
		} catch (Exception e) {
			throw new FacebookException(e);
		}
	}

	@Override
	public void removeOffer(String facebookOfferId)
			throws HttpFacebookException, FacebookException {
		try {
			HttpResponse response = Request
					.Delete(facebookApi + facebookOfferId + "/?access_token="
							+ facebookToken).execute().returnResponse();
			validateStatusCode(200, response, "removeOffer: ");
		} catch (HttpFacebookException e) {
			throw e;
		} catch (Exception e) {
			throw new FacebookException(e);
		}
	}

	@Override
	public String updateOffer(String facebookOfferId, Offer o)
			throws HttpFacebookException, FacebookException {
		try {
			HttpResponse response = Request
					.Delete(facebookApi + facebookOfferId + "/?access_token="
							+ facebookToken).execute().returnResponse();
			validateStatusCode(200, response, "UpdateOffer(add): ");
			response = Request
					.Post(facebookApi + facebookPageId + "/" + "feed")
					.bodyForm(
							Form.form().add("access_token", facebookToken)
									.add("message", "test").build()).execute()
					.returnResponse();
			validateStatusCode(200, response, "UpdateOffer(remove): ");

			return FacebookParser.toStringKey(
					response.getEntity().getContent(), "id");
		} catch (HttpFacebookException e) {
			throw e;
		} catch (Exception e) {
			throw new FacebookException(e);
		}
	}

	@Override
	public String updateOffer2(String facebookOfferId, Offer o)
			throws HttpFacebookException, FacebookException {
		try {
			HttpResponse response = Request
					.Post(facebookApi + facebookOfferId)
					.bodyForm(
							Form.form().add("access_token", facebookToken)
									.add("message", o.toString()).build())
					.execute().returnResponse();
			validateStatusCode(200, response, "UpdateOffer(remove): ");

			return facebookOfferId;
		} catch (HttpFacebookException e) {
			throw e;
		} catch (Exception e) {
			throw new FacebookException(e);
		}
	}

	@Override
	public Long getOfferLikes(String facebookOfferId)
			throws HttpFacebookException, FacebookException {
		Long likes = null;
		try {
			HttpResponse response = Request
					.Get(facebookApi + facebookOfferId + "/likes"
							+ "/?access_token=" + facebookToken).execute()
					.returnResponse();
			likes = FacebookParser.getLikesLength(response.getEntity()
					.getContent());
			validateStatusCode(200, response, "getOffersLikes: ");
		} catch (HttpFacebookException e) {
			throw e;
		} catch (Exception e) {
			throw new FacebookException(e);
		}
		return likes;
	}

	/*Check http response code. If check fail it trhows the necesary exception*/
	private void validateStatusCode(int successCode, HttpResponse response,
			String errPrefix) throws HttpFacebookException, FacebookException {

		try {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == successCode)
				return;
			throw new HttpFacebookException(statusCode, errPrefix
					+ FacebookParser.parseFacebookErrorMessage(response
							.getEntity().getContent()));

		} catch (HttpFacebookException e) {
			throw e;
		} catch (Exception e) {
			throw new FacebookException(e);
		}

	}
}
