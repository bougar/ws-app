package es.udc.ws.app.soapservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebService;

import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.dto.ReservationDto;
import es.udc.ws.app.dto.UserOfferDto;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offer.ReturnedOffer;
import es.udc.ws.app.model.offerservice.OfferServiceFactory;
import es.udc.ws.app.model.reservation.Reservation;
import es.udc.ws.app.serviceutil.OfferToOfferDtoConversor;
import es.udc.ws.app.serviceutil.ReservationToReservationDtoConversor;
import es.udc.ws.app.soapserviceexceptions.SoapAlreadyInvalidatedException;
import es.udc.ws.app.soapserviceexceptions.SoapAlreadyInvalidatedExceptionInfo;
import es.udc.ws.app.soapserviceexceptions.SoapAlreadyReservedException;
import es.udc.ws.app.soapserviceexceptions.SoapAlreadyReservedExceptionInfo;
import es.udc.ws.app.soapserviceexceptions.SoapInputValidationException;
import es.udc.ws.app.soapserviceexceptions.SoapInstanceNotFoundException;
import es.udc.ws.app.soapserviceexceptions.SoapInstanceNotFoundExceptionInfo;
import es.udc.ws.app.soapserviceexceptions.SoapNotClaimableException;
import es.udc.ws.app.soapserviceexceptions.SoapNotClaimableExceptionInfo;
import es.udc.ws.app.soapserviceexceptions.SoapNotModifiableOfferException;
import es.udc.ws.app.soapserviceexceptions.SoapNotModifiableOfferExceptionInfo;
import es.udc.ws.app.soapserviceexceptions.SoapReservationTimeExpiredException;
import es.udc.ws.app.soapserviceexceptions.SoapReservationTimeExpiredExceptionInfo;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

@WebService(
	name = "OfferProvider", 
	serviceName = "OfferProviderService", 
	targetNamespace = "http://soap.ws.app.udc.es/"
	)
public class SoapOfferService {

	public OfferDto addOffer(Offer offer) throws SoapInputValidationException {
		try {
			return OfferToOfferDtoConversor.toOfferDto(OfferServiceFactory
					.getService().addOffer(offer));
		} catch (InputValidationException e) {
			throw new SoapInputValidationException(e.getMessage());
		}
	}

	public void updateOffer(Offer offer) throws SoapInputValidationException,
			SoapInstanceNotFoundException, SoapNotModifiableOfferException {
		try {
			OfferServiceFactory.getService().updateOffer(offer);
		} catch (InputValidationException e) {
			throw new SoapInputValidationException(e.getMessage());
		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		} catch (NotModifiableOfferException e) {
			throw new SoapNotModifiableOfferException(
					new SoapNotModifiableOfferExceptionInfo(e.getOfferId()));
		}
	}

	public void removeOffer(long offerId) throws SoapInstanceNotFoundException,
			SoapNotModifiableOfferException {
		try {
			OfferServiceFactory.getService().removeOffer(offerId);
		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		} catch (NotModifiableOfferException e) {
			throw new SoapNotModifiableOfferException(
					new SoapNotModifiableOfferExceptionInfo(e.getOfferId()));
		}
	}

	public OfferDto findOffer(long offerId)
			throws SoapInstanceNotFoundException {
		OfferDto offerDto = null;
		try {
			offerDto = OfferToOfferDtoConversor.toOfferDto(OfferServiceFactory
					.getService().findOffer(offerId));
		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		}
		return offerDto;
	}

	public long reserveOffer(long offerId, String email, String creditCard)
			throws SoapInputValidationException, SoapInstanceNotFoundException,
			SoapAlreadyInvalidatedException,
			SoapReservationTimeExpiredException, SoapAlreadyReservedException {
		Long offer = null;
		try {
			offer = OfferServiceFactory.getService().reserveOffer(offerId,
					email, creditCard);
		} catch (InputValidationException e) {
			throw new SoapInputValidationException(e.getMessage());
		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		} catch (AlreadyInvalidatedException e) {
			throw new SoapAlreadyInvalidatedException(
					new SoapAlreadyInvalidatedExceptionInfo(e.getOfferId()));
		} catch (ReservationTimeExpiredException e) {
			throw new SoapReservationTimeExpiredException(
					new SoapReservationTimeExpiredExceptionInfo(e.getOfferId(),
							e.getLimitReservationDate()));
		} catch (AlreadyReservatedException e) {
			throw new SoapAlreadyReservedException(
					new SoapAlreadyReservedExceptionInfo(e.getOfferId(),
							e.getUser()));
		}
		return offer;
	}

	public void claimOffer(long reservationId, String user)
			throws SoapInstanceNotFoundException, SoapNotClaimableException {
		try {
			OfferServiceFactory.getService().claimOffer(reservationId, user);
		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		} catch (NotClaimableException e) {
			throw new SoapNotClaimableException(
					new SoapNotClaimableExceptionInfo(e.getReservationId(),
							e.getExpirationDate(), e.getEmail(), e.getState()));
		}
	}

	
	public List<OfferDto> findOffers(String keywords) {
		List<ReturnedOffer> offers = new ArrayList<ReturnedOffer>();
		List<OfferDto> offersDto = new ArrayList<OfferDto>();
		offers = OfferServiceFactory.getService().findOffers(keywords, true,
				Calendar.getInstance());
		for (ReturnedOffer o : offers)
			offersDto.add(OfferToOfferDtoConversor.toOfferDto(o));
		return offersDto;
	}

	public List<ReservationDto> findReservationByUser(String user,
			boolean showAll) {
		return ReservationToReservationDtoConversor
				.toReservationDtoList(OfferServiceFactory.getService()
						.findReservationByUser(user, showAll));
	}

	public void offerInvalidation(long offerId)
			throws SoapInstanceNotFoundException,
			SoapAlreadyInvalidatedException {
		try {
			OfferServiceFactory.getService().offerInvalidation(offerId);
		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		} catch (AlreadyInvalidatedException e) {
			throw new SoapAlreadyInvalidatedException(
					new SoapAlreadyInvalidatedExceptionInfo(e.getOfferId()));
		}
	}
	public List<ReservationDto> findReservationByOfferId(long offerId) throws SoapInstanceNotFoundException{
		try {
			return ReservationToReservationDtoConversor.toReservationDtoList(OfferServiceFactory.getService().findReservationByOfferId(offerId));
		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		}
	}
	public List<UserOfferDto> getUserOffersInfo(String user)
			throws SoapInstanceNotFoundException {
		List<Reservation> reservations = OfferServiceFactory.getService()
				.findReservationByUser(user, true);
		List<UserOfferDto> userOffers = new ArrayList<UserOfferDto>();
		Calendar requestDate = null;
		String description = null;
		float discountedPrice;
		for (Reservation r : reservations) {
			ReturnedOffer offer = null;
			try {
				offer = OfferServiceFactory.getService().findOffer(
						r.getOfferId());
			} catch (InstanceNotFoundException e) {
				throw new SoapInstanceNotFoundException(
						new SoapInstanceNotFoundExceptionInfo(
								e.getInstanceId(), e.getInstanceType()));
			}
			description = offer.getDescription();
			discountedPrice = offer.getDiscountedPrice();
			requestDate = r.getRequestDate();
			userOffers.add(new UserOfferDto(description, discountedPrice,
					requestDate));
		}
		return userOffers;
	}
}
