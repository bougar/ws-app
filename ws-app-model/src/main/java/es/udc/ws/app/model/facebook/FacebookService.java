package es.udc.ws.app.model.facebook;

import es.udc.ws.app.model.offer.Offer;

public interface FacebookService {
	public void addOffer(Offer o);
	public void removeOffer(String facebookOfferId);
	public long getOfferLikes(String facebookOfferId);
}
