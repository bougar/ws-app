package es.udc.ws.app.soapservice;

import javax.jws.WebService;

import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offerservice.OfferServiceFactory;
import es.udc.ws.app.serviceutil.OfferToOfferDtoConversor;
import es.udc.ws.app.soapserviceexceptions.SoapInputValidationException;
import es.udc.ws.app.soapserviceexceptions.SoapInstanceNotFoundException;
import es.udc.ws.app.soapserviceexceptions.SoapInstanceNotFoundExceptionInfo;
import es.udc.ws.app.soapserviceexceptions.SoapNotModifiableOfferException;
import es.udc.ws.app.soapserviceexceptions.SoapNotModifiableOfferExceptionInfo;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

@WebService(name = "OfferService", 
	serviceName = "OfferProviderService", 
	targetNamespace = "http://soap.ws.udc.es/offer"
	)
public class SoapOfferService {

	public SoapOfferService() {
	}

	public OfferDto addOffer(Offer offer) throws SoapInputValidationException {
		// Offer offer = OfferToOfferDtoConversor.toOffer(offerDto, fee);
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
	
	public OfferDto findOffer(long offerId) throws SoapInstanceNotFoundException{
		OfferDto offerDto=null;
		try {
			offerDto=OfferToOfferDtoConversor.toOfferDto(OfferServiceFactory.getService().findOffer(offerId));
		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		}
		return offerDto;
	}
	
	
}
