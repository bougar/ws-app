package es.udc.ws.app.soapservice;

import javax.jws.WebService;

import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offerservice.OfferService;
import es.udc.ws.app.model.offerservice.OfferServiceFactory;
import es.udc.ws.app.serviceutil.OfferToOfferDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;

@WebService(
	    name="OfferService",
	    serviceName="OfferProviderService",
	    targetNamespace="http://soap.ws.udc.es/offer"
	)
public class SoapOfferService {
	
	public SoapOfferService(){
	}
	
	public OfferDto addOffer(Offer offer){
		try {
			return OfferToOfferDtoConversor.toOfferDto(OfferServiceFactory.getService().addOffer(offer));
		} catch (InputValidationException e) {
			 throw new RuntimeException(e);//Change this
		}
	}
}
