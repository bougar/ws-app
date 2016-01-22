package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;



public class OfferToSoapOfferConversions {
	public static es.udc.ws.app.dto.CreationOfferDto toClientOffer(
			es.udc.ws.app.client.service.soap.wsdl.Offer offer) {
		Calendar limitReservationDate = Calendar.getInstance();
		limitReservationDate.setTime(offer.getLimitReservationDate()
				.toGregorianCalendar().getTime());
		Calendar limitApplicationDate = Calendar.getInstance();
		limitApplicationDate.setTime(offer.getLimitApplicationDate()
				.toGregorianCalendar().getTime());
		return new es.udc.ws.app.dto.CreationOfferDto(offer.getOfferId(),
				offer.getName(), offer.getDescription(), limitReservationDate,
				limitApplicationDate, offer.getRealPrice(),
				offer.getDiscountedPrice(), offer.getFee(), offer.isValid());
	}

	public static es.udc.ws.app.client.service.soap.wsdl.Offer toSoapOffer(
			es.udc.ws.app.dto.CreationOfferDto offer) {
		XMLGregorianCalendar limitReservationDate = null;
		XMLGregorianCalendar limitApplicationDate = null;
		GregorianCalendar aux = null;

		try {
			aux = new GregorianCalendar();
			aux.setTime(offer.getLimitReservationDate().getTime());
			limitReservationDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(aux);
			aux = new GregorianCalendar();
			aux.setTime(offer.getLimitApplicationDate().getTime());
			limitApplicationDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(aux);
		} catch (DatatypeConfigurationException e) {
			System.err.println("Error parsing date to xml date.");
			e.printStackTrace();
		}
		es.udc.ws.app.client.service.soap.wsdl.Offer soapOffer = new es.udc.ws.app.client.service.soap.wsdl.Offer();
		soapOffer.setOfferId(offer.getOfferId());
		soapOffer.setName(offer.getName());
		soapOffer.setDescription(offer.getDescription());
		soapOffer.setLimitReservationDate(limitReservationDate);
		soapOffer.setLimitApplicationDate(limitApplicationDate);
		soapOffer.setRealPrice(offer.getRealPrice());
		soapOffer.setDiscountedPrice(offer.getDiscountedPrice());
		soapOffer.setFee(offer.getFee());
		return soapOffer;
	}

	public static es.udc.ws.app.dto.OfferDto toClientOfferDto(
			es.udc.ws.app.client.service.soap.wsdl.OfferDto offer) {
		Calendar limitReservationDate = Calendar.getInstance();
		limitReservationDate.setTime(offer.getLimitReservationDate()
				.toGregorianCalendar().getTime());
		Calendar limitApplicationDate = Calendar.getInstance();
		limitApplicationDate.setTime(offer.getLimitApplicationDate()
				.toGregorianCalendar().getTime());
		return new es.udc.ws.app.dto.OfferDto(offer.getOfferId(),
				offer.getName(), offer.getDescription(), limitReservationDate,
				limitApplicationDate, offer.getRealPrice(),
				offer.getDiscountedPrice(), offer.isValid(), offer.getLikes());
	}

	public static es.udc.ws.app.client.service.soap.wsdl.OfferDto toSoapOfferDto(
			es.udc.ws.app.dto.OfferDto offer) {
		XMLGregorianCalendar limitReservationDate = null;
		XMLGregorianCalendar limitApplicationDate = null;
		GregorianCalendar aux = null;

		try {
			aux = new GregorianCalendar();
			aux.setTime(offer.getLimitReservationDate().getTime());
			limitReservationDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(aux);
			aux = new GregorianCalendar();
			aux.setTime(offer.getLimitApplicationDate().getTime());
			limitApplicationDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(aux);
		} catch (DatatypeConfigurationException e) {
			System.err.println("Error parsing date to xml date.");
			e.printStackTrace();
		}
		es.udc.ws.app.client.service.soap.wsdl.OfferDto soapOffer = new es.udc.ws.app.client.service.soap.wsdl.OfferDto();
		soapOffer.setOfferId(offer.getOfferId());
		soapOffer.setName(offer.getName());
		soapOffer.setDescription(offer.getDescription());
		soapOffer.setLimitReservationDate(limitReservationDate);
		soapOffer.setLimitApplicationDate(limitApplicationDate);
		soapOffer.setRealPrice(offer.getRealPrice());
		soapOffer.setDiscountedPrice(offer.getDiscountedPrice());
		soapOffer.setValid(offer.isValid());
		soapOffer.setLikes(offer.getLikes());
		return soapOffer;
	}

	public static List<es.udc.ws.app.dto.OfferDto> toClientListOffertDto(
			List<es.udc.ws.app.client.service.soap.wsdl.OfferDto> offersDto) {
		List<es.udc.ws.app.dto.OfferDto> clientOffersDto = new ArrayList<es.udc.ws.app.dto.OfferDto>();
		for (es.udc.ws.app.client.service.soap.wsdl.OfferDto o : offersDto)
			clientOffersDto
					.add(OfferToSoapOfferConversions.toClientOfferDto(o));
		return clientOffersDto;
	}

}
