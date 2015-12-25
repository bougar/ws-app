package es.udc.ws.app.client.service;

import java.util.List;

import es.udc.ws.app.dto.CreationOfferDto;
import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.dto.ReservationDto;
import es.udc.ws.app.dto.UserOfferDto;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ClientOfferService {

	public OfferDto addOffer(CreationOfferDto offer) throws InputValidationException;

	public void updateOffer(CreationOfferDto offer) throws InputValidationException,
			InstanceNotFoundException, NotModifiableOfferException;

	public void removeOffer(long offerId) throws InstanceNotFoundException,
			NotModifiableOfferException;

	public OfferDto findOffer(long offerId) throws InstanceNotFoundException;

	public List<OfferDto> findOffers(String keywords);

	public long reserveOffer(long offerId, String email, String creditCardNumber)
			throws InputValidationException, InstanceNotFoundException,
			AlreadyInvalidatedException, ReservationTimeExpiredException,
			AlreadyReservatedException;

	public void claimOffer(long reservationId, String email)
			throws InstanceNotFoundException, NotClaimableException;

	public List<ReservationDto> findReservationByOfferId(long offerId)
			throws InstanceNotFoundException;

	public List<ReservationDto> findReservationByUser(String email, boolean state);

	public void offerInvalidation(long offerId)
			throws InstanceNotFoundException, AlreadyInvalidatedException;

	public List<UserOfferDto> getUserOffersInfo(String user)
			throws InstanceNotFoundException;

}