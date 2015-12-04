package es.udc.ws.app.serviceutil;

import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.model.offer.Offer;

public class OfferToOfferDtoConversor {
	public static OfferDto toOfferDto(Offer offer) {
		return new OfferDto(offer.getOfferId(), offer.getName(),
				offer.getDescription(),
				offer.getLimitReservationDate(),
				offer.getLimitApplicationDate(), offer.getRealPrice(),
				offer.getDiscountedPrice(), offer.isValid());
	}
}