package es.udc.ws.app.model.facebook;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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
		facebookPageId = ConfigurationParametersManager
				.getParameter(FACEBOOK_PAGE_ID_PARAM);
		facebookToken = ConfigurationParametersManager
				.getParameter(FACEBOOK_TOKEN_PARAM);
		facebookApi = ConfigurationParametersManager
				.getParameter(FACEBOOK_API_PARAM);
	}

	@Override
	public void addOffer(Offer o) {
		try {
			HttpResponse response = Request
					.Post(facebookApi)
					.bodyForm(
							Form.form().add("access_token", facebookToken)
									.add("message", o.toString()).build())
					.execute().returnResponse();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void removeOffer(String facebookOfferId) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getOfferLikes(String facebookOfferId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
