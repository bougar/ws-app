package es.udc.ws.app.model.offer;

import java.util.ArrayList;
import java.util.List;

public class OfferToReturnedOffer {
	public static ReturnedOffer toReturnedOffer(Offer o) {
		return new ReturnedOffer(o.getOfferId(),o.getName(), o.getDescription(),
				o.getLimitReservationDate(), o.getLimitApplicationDate(),
				o.getRealPrice(), o.getDiscountedPrice(), o.getFee(),
				o.isValid());
	}

	public static List<ReturnedOffer> toReturnedOfferList(List<Offer> offers) {
		List<ReturnedOffer> returnedOffers = new ArrayList<ReturnedOffer>();
		for (Offer o : offers) {
			returnedOffers.add(toReturnedOffer(o));
		}
		return returnedOffers;
	}

	public static Offer toOffer(ReturnedOffer o) {
		return new Offer(o.getOfferId(), o.getName(), o.getDescription(),
				o.getLimitReservationDate(), o.getLimitApplicationDate(),
				o.getRealPrice(), o.getDiscountedPrice(), o.getFee(),
				o.isValid(), null);
	}

	public static List<Offer> toOfferList(List<ReturnedOffer> offers) {
		List<Offer> returnedOffers = new ArrayList<Offer>();
		for (ReturnedOffer o : offers) {
			returnedOffers.add(toOffer(o));
		}
		return returnedOffers;
	}
}
