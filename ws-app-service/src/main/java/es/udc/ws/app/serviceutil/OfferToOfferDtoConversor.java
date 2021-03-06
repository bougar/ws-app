package es.udc.ws.app.serviceutil;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offer.ReturnedOffer;

public class OfferToOfferDtoConversor {
	public static OfferDto toOfferDto(ReturnedOffer offer) {
		return new OfferDto(offer.getOfferId(), offer.getName(),
				offer.getDescription(),
				offer.getLimitReservationDate(),
				offer.getLimitApplicationDate(), offer.getRealPrice(),
				offer.getDiscountedPrice(), offer.isValid(), offer.getLikes());
	}
	
	public static List<OfferDto> toOffersDto(List<ReturnedOffer> offers){
		List<OfferDto> offersDto = new ArrayList<OfferDto>();
		for (ReturnedOffer o : offers)
			offersDto.add(toOfferDto(o));
		return offersDto;
			
	}
	public static Offer toOffer(OfferDto offerDto,long fee){
		return new Offer(offerDto.getOfferId(), offerDto.getName(),
				offerDto.getDescription(),
				offerDto.getLimitReservationDate(),
				offerDto.getLimitApplicationDate(), offerDto.getRealPrice(),
				offerDto.getDiscountedPrice(),fee, offerDto.isValid(),null);
	}
}