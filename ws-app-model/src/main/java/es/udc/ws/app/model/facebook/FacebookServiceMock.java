package es.udc.ws.app.model.facebook;

import es.udc.ws.app.model.offer.Offer;

public class FacebookServiceMock  implements FacebookService {

	@Override
	public String addOffer(Offer o) throws HttpFacebookException,
			FacebookException {
		return "a";
	}

	@Override
	public void removeOffer(String facebookOfferId)
			throws HttpFacebookException, FacebookException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getOfferLikes(String facebookOfferId)
			throws HttpFacebookException, FacebookException {
		return Long.valueOf(5);
	}

	@Override
	public String updateOffer(String facebookOfferId, Offer o)
			throws HttpFacebookException, FacebookException {
		// TODO Auto-generated method stub
		return "b";
	}

	@Override
	public String updateOffer2(String facebookOfferId, Offer o)
			throws HttpFacebookException, FacebookException {
		// TODO Auto-generated method stub
		return null;
	}

}
