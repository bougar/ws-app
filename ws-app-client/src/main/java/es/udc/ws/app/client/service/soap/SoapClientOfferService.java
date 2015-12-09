package es.udc.ws.app.client.service.soap;

import java.util.Calendar;
import java.util.List;

import javax.xml.ws.BindingProvider;

import es.udc.ws.app.client.service.ClientOfferService;
import es.udc.ws.app.client.types.Offer;
import es.udc.ws.app.client.types.OfferDto;
import es.udc.ws.app.client.types.ReservationDto;
import es.udc.ws.app.client.types.UserOfferDto;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.client.service.soap.wsdl.*;

public class SoapClientOfferService implements ClientOfferService {
	private final static String ENDPOINT_ADDRESS_PARAMETER = "SoapClientMovieService.endpointAddress";

	private String endpointAddress;

	private OfferProvider offerProvider;

	public SoapClientOfferService() {
		init(getEndpointAddress());
	}

	private void init(String moviesProviderURL) {
		OfferProviderService offerProviderService = new OfferProviderService();
		offerProvider = offerProviderService.getOfferProviderPort();
		((BindingProvider) offerProvider).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, moviesProviderURL);
	}

	@Override
	public OfferDto addOffer(Offer offer) throws InputValidationException {
		try {
			return OfferToSoapOfferConversions.toClientOfferDto(offerProvider
					.addOffer(OfferToSoapOfferConversions.toSoapOffer(offer)));
		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getFaultInfo());
		}
	}

	@Override
	public void updateOffer(Offer offer) throws InputValidationException,
			InstanceNotFoundException, NotModifiableOfferException {
		try {
			offerProvider.updateOffer(OfferToSoapOfferConversions
					.toSoapOffer(offer));
		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getFaultInfo());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (SoapNotModifiableOfferException e) {
			throw new NotModifiableOfferException(e.getFaultInfo().getOfferId());
		}

	}

	@Override
	public void removeOffer(long offerId) throws InstanceNotFoundException,
			NotModifiableOfferException {
		try {
			offerProvider.removeOffer(offerId);
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (SoapNotModifiableOfferException e) {
			throw new NotModifiableOfferException(e.getFaultInfo().getOfferId());
		}

	}

	@Override
	public OfferDto findOffer(long offerId) throws InstanceNotFoundException {
		OfferDto offer = null;
		try {
			offer = OfferToSoapOfferConversions.toClientOfferDto(offerProvider
					.findOffer(offerId));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		}
		return offer;
	}

	@Override
	public List<OfferDto> findOffers(String keywords) {
		return OfferToSoapOfferConversions.toClientListOffertDto(offerProvider
				.findOffers(keywords));
	}

	@Override
	public long reserveOffer(long offerId, String email, String creditCardNumber)
			throws InputValidationException, InstanceNotFoundException,
			AlreadyInvalidatedException, ReservationTimeExpiredException,
			AlreadyReservatedException {
		try {
			return offerProvider.reserveOffer(offerId, email, creditCardNumber);
		} catch (SoapAlreadyInvalidatedException e) {
			throw new AlreadyInvalidatedException(e.getFaultInfo().getOfferId());
		} catch (SoapAlreadyReservedException e) {
			throw new AlreadyReservatedException(e.getFaultInfo().getOfferId(),
					e.getFaultInfo().getUser());
		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getFaultInfo());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (SoapReservationTimeExpiredException e) {
			Calendar limitReservationDate = Calendar.getInstance();
			limitReservationDate.setTime(e.getFaultInfo()
					.getLimitReservationDate().toGregorianCalendar().getTime());
			throw new ReservationTimeExpiredException(e.getFaultInfo()
					.getOfferId(), limitReservationDate);
		}
	}

	@Override
	public void claimOffer(long reservationId, String email)
			throws InstanceNotFoundException, NotClaimableException {
		try {
			offerProvider.claimOffer(reservationId, email);
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (SoapNotClaimableException e) {
			throw new NotClaimableException(e.getFaultInfo().getEmail());
		}

	}

	@Override
	public List<ReservationDto> findReservationByOfferId(long offerId)
			throws InstanceNotFoundException {
		try {
			return ReservationToSoapReservationConversions.toClientReservationList(offerProvider.findReservationByOfferId(offerId));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		}
	}

	@Override
	public List<ReservationDto> findReservationByUser(String email,
			boolean showAll) {
		return ReservationToSoapReservationConversions
				.toClientReservationList(offerProvider.findReservationByUser(
						email, showAll));
	}

	@Override
	public void offerInvalidation(long offerId)
			throws InstanceNotFoundException, AlreadyInvalidatedException {
		try {
			offerProvider.offerInvalidation(offerId);
		} catch (SoapAlreadyInvalidatedException e) {
			throw new AlreadyInvalidatedException(e.getFaultInfo().getOfferId());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		}

	}

	@Override
	public List<UserOfferDto> getUserOffersInfo(String user)
			throws InstanceNotFoundException {
		try {
			return UserOfferDtoToSoapUserOfferDtoConversions
					.toClientUserOffersDtoList(offerProvider
							.getUserOffersInfo(user));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		}
	}

	private String getEndpointAddress() {

		if (endpointAddress == null) {
			endpointAddress = ConfigurationParametersManager
					.getParameter(ENDPOINT_ADDRESS_PARAMETER);
		}

		return endpointAddress;
	}

}
