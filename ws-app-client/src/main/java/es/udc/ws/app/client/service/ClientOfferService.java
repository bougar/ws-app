package es.udc.ws.app.client.service;

import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.app.client.types.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ClientOfferService {

	public OfferDto addOffer(Offer offer) throws InputValidationException;

	public void updateOffer(Offer offer) throws InputValidationException,
			InstanceNotFoundException, NotModifiableOfferException;

	public void removeOffer(long offerId) throws InstanceNotFoundException,
			NotModifiableOfferException;

	public OfferDto findOffer(long offerId) throws InstanceNotFoundException;

	public List<OfferDto> findOffers(String keywords, Boolean state,
			Calendar date);

	public long reserveOffer(long offerId, String email, String creditCardNumber)
			throws InputValidationException, InstanceNotFoundException,
			AlreadyInvalidatedException, ReservationTimeExpiredException,
			AlreadyReservatedException;

	public void claimOffer(long reservationId, String email)
			throws InstanceNotFoundException, NotClaimableException;

	public List<Reservation> findReservationByOfferId(long offerId)
			throws InstanceNotFoundException;

	public List<Reservation> findReservationByUser(String email, boolean state);

	public void offerInvalidation(long offerId)
			throws InstanceNotFoundException, AlreadyInvalidatedException;

	public List<UserOfferDto> getUserOffersInfo(String user)
			throws InstanceNotFoundException;

}