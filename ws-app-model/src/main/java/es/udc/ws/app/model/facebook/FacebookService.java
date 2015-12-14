package es.udc.ws.app.model.facebook;

import es.udc.ws.app.model.offer.Offer;

public interface FacebookService {
	public String addOffer(Offer o);
	public void removeOffer(String facebookOfferId);
	public Long getOfferLikes(String facebookOfferId);
	public String updateOffer(String facebookOfferId,Offer o);
}
