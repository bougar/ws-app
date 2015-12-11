
package es.udc.ws.app.client.service.soap.wsdl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "OfferProvider", targetNamespace = "http://soap.ws.app.udc.es/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface OfferProvider {


    /**
     * 
     * @param arg0
     * @return
     *     returns es.udc.ws.app.client.service.soap.wsdl.OfferDto
     * @throws SoapInputValidationException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addOffer", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.AddOffer")
    @ResponseWrapper(localName = "addOfferResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.AddOfferResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/addOfferRequest", output = "http://soap.ws.app.udc.es/OfferProvider/addOfferResponse", fault = {
        @FaultAction(className = SoapInputValidationException.class, value = "http://soap.ws.app.udc.es/OfferProvider/addOffer/Fault/SoapInputValidationException")
    })
    public OfferDto addOffer(
        @WebParam(name = "arg0", targetNamespace = "")
        Offer arg0)
        throws SoapInputValidationException
    ;

    /**
     * 
     * @param arg0
     * @throws SoapInstanceNotFoundException
     * @throws SoapNotModifiableOfferException
     */
    @WebMethod
    @RequestWrapper(localName = "removeOffer", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.RemoveOffer")
    @ResponseWrapper(localName = "removeOfferResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.RemoveOfferResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/removeOfferRequest", output = "http://soap.ws.app.udc.es/OfferProvider/removeOfferResponse", fault = {
        @FaultAction(className = SoapInstanceNotFoundException.class, value = "http://soap.ws.app.udc.es/OfferProvider/removeOffer/Fault/SoapInstanceNotFoundException"),
        @FaultAction(className = SoapNotModifiableOfferException.class, value = "http://soap.ws.app.udc.es/OfferProvider/removeOffer/Fault/SoapNotModifiableOfferException")
    })
    public void removeOffer(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0)
        throws SoapInstanceNotFoundException, SoapNotModifiableOfferException
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns es.udc.ws.app.client.service.soap.wsdl.OfferDto
     * @throws SoapInstanceNotFoundException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findOffer", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.FindOffer")
    @ResponseWrapper(localName = "findOfferResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.FindOfferResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/findOfferRequest", output = "http://soap.ws.app.udc.es/OfferProvider/findOfferResponse", fault = {
        @FaultAction(className = SoapInstanceNotFoundException.class, value = "http://soap.ws.app.udc.es/OfferProvider/findOffer/Fault/SoapInstanceNotFoundException")
    })
    public OfferDto findOffer(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0)
        throws SoapInstanceNotFoundException
    ;

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns long
     * @throws SoapReservationTimeExpiredException
     * @throws SoapInstanceNotFoundException
     * @throws SoapInputValidationException
     * @throws SoapAlreadyReservedException
     * @throws SoapAlreadyInvalidatedException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "reserveOffer", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.ReserveOffer")
    @ResponseWrapper(localName = "reserveOfferResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.ReserveOfferResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/reserveOfferRequest", output = "http://soap.ws.app.udc.es/OfferProvider/reserveOfferResponse", fault = {
        @FaultAction(className = SoapInputValidationException.class, value = "http://soap.ws.app.udc.es/OfferProvider/reserveOffer/Fault/SoapInputValidationException"),
        @FaultAction(className = SoapInstanceNotFoundException.class, value = "http://soap.ws.app.udc.es/OfferProvider/reserveOffer/Fault/SoapInstanceNotFoundException"),
        @FaultAction(className = SoapAlreadyInvalidatedException.class, value = "http://soap.ws.app.udc.es/OfferProvider/reserveOffer/Fault/SoapAlreadyInvalidatedException"),
        @FaultAction(className = SoapReservationTimeExpiredException.class, value = "http://soap.ws.app.udc.es/OfferProvider/reserveOffer/Fault/SoapReservationTimeExpiredException"),
        @FaultAction(className = SoapAlreadyReservedException.class, value = "http://soap.ws.app.udc.es/OfferProvider/reserveOffer/Fault/SoapAlreadyReservedException")
    })
    public long reserveOffer(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2)
        throws SoapAlreadyInvalidatedException, SoapAlreadyReservedException, SoapInputValidationException, SoapInstanceNotFoundException, SoapReservationTimeExpiredException
    ;

    /**
     * 
     * @param arg0
     * @throws SoapInstanceNotFoundException
     * @throws SoapInputValidationException
     * @throws SoapNotModifiableOfferException
     */
    @WebMethod
    @RequestWrapper(localName = "updateOffer", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.UpdateOffer")
    @ResponseWrapper(localName = "updateOfferResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.UpdateOfferResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/updateOfferRequest", output = "http://soap.ws.app.udc.es/OfferProvider/updateOfferResponse", fault = {
        @FaultAction(className = SoapInputValidationException.class, value = "http://soap.ws.app.udc.es/OfferProvider/updateOffer/Fault/SoapInputValidationException"),
        @FaultAction(className = SoapInstanceNotFoundException.class, value = "http://soap.ws.app.udc.es/OfferProvider/updateOffer/Fault/SoapInstanceNotFoundException"),
        @FaultAction(className = SoapNotModifiableOfferException.class, value = "http://soap.ws.app.udc.es/OfferProvider/updateOffer/Fault/SoapNotModifiableOfferException")
    })
    public void updateOffer(
        @WebParam(name = "arg0", targetNamespace = "")
        Offer arg0)
        throws SoapInputValidationException, SoapInstanceNotFoundException, SoapNotModifiableOfferException
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @throws SoapInstanceNotFoundException
     * @throws SoapNotClaimableException
     */
    @WebMethod
    @RequestWrapper(localName = "claimOffer", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.ClaimOffer")
    @ResponseWrapper(localName = "claimOfferResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.ClaimOfferResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/claimOfferRequest", output = "http://soap.ws.app.udc.es/OfferProvider/claimOfferResponse", fault = {
        @FaultAction(className = SoapInstanceNotFoundException.class, value = "http://soap.ws.app.udc.es/OfferProvider/claimOffer/Fault/SoapInstanceNotFoundException"),
        @FaultAction(className = SoapNotClaimableException.class, value = "http://soap.ws.app.udc.es/OfferProvider/claimOffer/Fault/SoapNotClaimableException")
    })
    public void claimOffer(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1)
        throws SoapInstanceNotFoundException, SoapNotClaimableException
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<es.udc.ws.app.client.service.soap.wsdl.OfferDto>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findOffers", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.FindOffers")
    @ResponseWrapper(localName = "findOffersResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.FindOffersResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/findOffersRequest", output = "http://soap.ws.app.udc.es/OfferProvider/findOffersResponse")
    public List<OfferDto> findOffers(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<es.udc.ws.app.client.service.soap.wsdl.ReservationDto>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findReservationByUser", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.FindReservationByUser")
    @ResponseWrapper(localName = "findReservationByUserResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.FindReservationByUserResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/findReservationByUserRequest", output = "http://soap.ws.app.udc.es/OfferProvider/findReservationByUserResponse")
    public List<ReservationDto> findReservationByUser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        boolean arg1);

    /**
     * 
     * @param arg0
     * @throws SoapInstanceNotFoundException
     * @throws SoapAlreadyInvalidatedException
     */
    @WebMethod
    @RequestWrapper(localName = "offerInvalidation", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.OfferInvalidation")
    @ResponseWrapper(localName = "offerInvalidationResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.OfferInvalidationResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/offerInvalidationRequest", output = "http://soap.ws.app.udc.es/OfferProvider/offerInvalidationResponse", fault = {
        @FaultAction(className = SoapInstanceNotFoundException.class, value = "http://soap.ws.app.udc.es/OfferProvider/offerInvalidation/Fault/SoapInstanceNotFoundException"),
        @FaultAction(className = SoapAlreadyInvalidatedException.class, value = "http://soap.ws.app.udc.es/OfferProvider/offerInvalidation/Fault/SoapAlreadyInvalidatedException")
    })
    public void offerInvalidation(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0)
        throws SoapAlreadyInvalidatedException, SoapInstanceNotFoundException
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<es.udc.ws.app.client.service.soap.wsdl.ReservationDto>
     * @throws SoapInstanceNotFoundException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findReservationByOfferId", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.FindReservationByOfferId")
    @ResponseWrapper(localName = "findReservationByOfferIdResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.FindReservationByOfferIdResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/findReservationByOfferIdRequest", output = "http://soap.ws.app.udc.es/OfferProvider/findReservationByOfferIdResponse", fault = {
        @FaultAction(className = SoapInstanceNotFoundException.class, value = "http://soap.ws.app.udc.es/OfferProvider/findReservationByOfferId/Fault/SoapInstanceNotFoundException")
    })
    public List<ReservationDto> findReservationByOfferId(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0)
        throws SoapInstanceNotFoundException
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<es.udc.ws.app.client.service.soap.wsdl.UserOfferDto>
     * @throws SoapInstanceNotFoundException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUserOffersInfo", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.GetUserOffersInfo")
    @ResponseWrapper(localName = "getUserOffersInfoResponse", targetNamespace = "http://soap.ws.app.udc.es/", className = "es.udc.ws.app.client.service.soap.wsdl.GetUserOffersInfoResponse")
    @Action(input = "http://soap.ws.app.udc.es/OfferProvider/getUserOffersInfoRequest", output = "http://soap.ws.app.udc.es/OfferProvider/getUserOffersInfoResponse", fault = {
        @FaultAction(className = SoapInstanceNotFoundException.class, value = "http://soap.ws.app.udc.es/OfferProvider/getUserOffersInfo/Fault/SoapInstanceNotFoundException")
    })
    public List<UserOfferDto> getUserOffersInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws SoapInstanceNotFoundException
    ;

}
