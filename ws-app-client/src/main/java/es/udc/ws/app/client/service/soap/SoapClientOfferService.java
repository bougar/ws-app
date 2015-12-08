package es.udc.ws.app.client.service.soap;

import java.util.Calendar;
import java.util.List;

import javax.xml.ws.BindingProvider;

import es.udc.ws.app.client.service.soap.wsdl.*;
import es.udc.ws.app.client.service.ClientOfferService;
import es.udc.ws.app.client.types.Offer;
import es.udc.ws.app.client.types.OfferDto;
import es.udc.ws.app.client.types.Reservation;
import es.udc.ws.app.client.types.UserOfferDto;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class SoapClientOfferService implements ClientOfferService{
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OfferDto> findOffers(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long reserveOffer(long offerId, String email, String creditCardNumber)
			throws InputValidationException, InstanceNotFoundException,
			AlreadyInvalidatedException, ReservationTimeExpiredException,
			AlreadyReservatedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void claimOffer(long reservationId, String email)
			throws InstanceNotFoundException, NotClaimableException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Reservation> findReservationByOfferId(long offerId)
			throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> findReservationByUser(String email, boolean state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void offerInvalidation(long offerId)
			throws InstanceNotFoundException, AlreadyInvalidatedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserOfferDto> getUserOffersInfo(String user)
			throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
    private String getEndpointAddress() {

        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager.getParameter(
                ENDPOINT_ADDRESS_PARAMETER);
        }

        return endpointAddress;
    }

}
