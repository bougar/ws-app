package es.udc.ws.app.serviceutil;

import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.model.facebook.FacebookServiceImpl;
import es.udc.ws.app.model.offer.Offer;

public class OfferToOfferDtoConversor {
	public static OfferDto toOfferDto(Offer offer) {
		return new OfferDto(offer.getOfferId(), offer.getName(),
				offer.getDescription(),
				offer.getLimitReservationDate(),
				offer.getLimitApplicationDate(), offer.getRealPrice(),
				offer.getDiscountedPrice(), offer.isValid(), offer.getFaceBookId(), offer.getLikes());
	}
	
	public static Offer toOffer(OfferDto offerDto,long fee){
		return new Offer(offerDto.getOfferId(), offerDto.getName(),
				offerDto.getDescription(),
				offerDto.getLimitReservationDate(),
				offerDto.getLimitApplicationDate(), offerDto.getRealPrice(),
				offerDto.getDiscountedPrice(),fee, offerDto.isValid(), offerDto.getFaceBookId());
	}
}