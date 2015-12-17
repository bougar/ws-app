package es.udc.ws.app.model.facebook;

import es.udc.ws.app.model.offer.Offer;

public interface FacebookService {
	public String addOffer(Offer o) throws HttpFacebookException,
			FacebookException;

	public void removeOffer(String facebookOfferId)
			throws HttpFacebookException, FacebookException;

	public Long getOfferLikes(String facebookOfferId)
			throws HttpFacebookException, FacebookException;

	public String updateOffer(String facebookOfferId, Offer o)
			throws HttpFacebookException, FacebookException;

	public String updateOffer2(String facebookOfferId, Offer o)
			throws HttpFacebookException, FacebookException;
}
