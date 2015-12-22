package es.udc.ws.app.serviceutil;


import es.udc.ws.app.dto.CreationOfferDto;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offer.ReturnedOffer;

public class OfferToCreationOfferDtoConversor {
	public static CreationOfferDto toOfferDto(ReturnedOffer offer) {
		return new CreationOfferDto(offer.getName(), offer.getDescription(),
				offer.getLimitReservationDate(),
				offer.getLimitApplicationDate(), offer.getRealPrice(),
				offer.getDiscountedPrice(), offer.getFee());
	}

	public static Offer toOffer(CreationOfferDto creationOfferDto){
		return new Offer(creationOfferDto.getOfferId(), creationOfferDto.getName(), creationOfferDto.getDescription(),
				creationOfferDto.getLimitReservationDate(), creationOfferDto.getLimitApplicationDate(),
				creationOfferDto.getRealPrice(), creationOfferDto.getDiscountedPrice(), creationOfferDto.getFee(), true,
				null);
	}
}