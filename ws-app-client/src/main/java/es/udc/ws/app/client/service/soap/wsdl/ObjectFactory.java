
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.udc.ws.app.client.service.soap.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FindOffers_QNAME = new QName("http://soap.ws.app.udc.es/", "findOffers");
    private final static QName _AddOffer_QNAME = new QName("http://soap.ws.app.udc.es/", "addOffer");
    private final static QName _FindOffer_QNAME = new QName("http://soap.ws.app.udc.es/", "findOffer");
    private final static QName _ReserveOffer_QNAME = new QName("http://soap.ws.app.udc.es/", "reserveOffer");
    private final static QName _ClaimOfferResponse_QNAME = new QName("http://soap.ws.app.udc.es/", "claimOfferResponse");
    private final static QName _FindReservationByUserResponse_QNAME = new QName("http://soap.ws.app.udc.es/", "findReservationByUserResponse");
    private final static QName _SoapInstanceNotFoundException_QNAME = new QName("http://soap.ws.app.udc.es/", "SoapInstanceNotFoundException");
    private final static QName _FindOfferResponse_QNAME = new QName("http://soap.ws.app.udc.es/", "findOfferResponse");
    private final static QName _SoapReservationTimeExpiredException_QNAME = new QName("http://soap.ws.app.udc.es/", "SoapReservationTimeExpiredException");
    private final static QName _OfferInvalidation_QNAME = new QName("http://soap.ws.app.udc.es/", "offerInvalidation");
    private final static QName _SoapNotModifiableOfferException_QNAME = new QName("http://soap.ws.app.udc.es/", "SoapNotModifiableOfferException");
    private final static QName _FindOffersResponse_QNAME = new QName("http://soap.ws.app.udc.es/", "findOffersResponse");
    private final static QName _ClaimOffer_QNAME = new QName("http://soap.ws.app.udc.es/", "claimOffer");
    private final static QName _UpdateOffer_QNAME = new QName("http://soap.ws.app.udc.es/", "updateOffer");
    private final static QName _SoapAlreadyReservedException_QNAME = new QName("http://soap.ws.app.udc.es/", "SoapAlreadyReservedException");
    private final static QName _SoapNotClaimableExceptionInfo_QNAME = new QName("http://soap.ws.app.udc.es/", "SoapNotClaimableExceptionInfo");
    private final static QName _FindReservationByUser_QNAME = new QName("http://soap.ws.app.udc.es/", "findReservationByUser");
    private final static QName _GetUserOffersInfo_QNAME = new QName("http://soap.ws.app.udc.es/", "getUserOffersInfo");
    private final static QName _RemoveOffer_QNAME = new QName("http://soap.ws.app.udc.es/", "removeOffer");
    private final static QName _OfferInvalidationResponse_QNAME = new QName("http://soap.ws.app.udc.es/", "offerInvalidationResponse");
    private final static QName _RemoveOfferResponse_QNAME = new QName("http://soap.ws.app.udc.es/", "removeOfferResponse");
    private final static QName _SoapAlreadyInvalidatedException_QNAME = new QName("http://soap.ws.app.udc.es/", "SoapAlreadyInvalidatedException");
    private final static QName _SoapInputValidationException_QNAME = new QName("http://soap.ws.app.udc.es/", "SoapInputValidationException");
    private final static QName _AddOfferResponse_QNAME = new QName("http://soap.ws.app.udc.es/", "addOfferResponse");
    private final static QName _GetUserOffersInfoResponse_QNAME = new QName("http://soap.ws.app.udc.es/", "getUserOffersInfoResponse");
    private final static QName _ReserveOfferResponse_QNAME = new QName("http://soap.ws.app.udc.es/", "reserveOfferResponse");
    private final static QName _UpdateOfferResponse_QNAME = new QName("http://soap.ws.app.udc.es/", "updateOfferResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.udc.ws.app.client.service.soap.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ClaimOfferResponse }
     * 
     */
    public ClaimOfferResponse createClaimOfferResponse() {
        return new ClaimOfferResponse();
    }

    /**
     * Create an instance of {@link FindReservationByUserResponse }
     * 
     */
    public FindReservationByUserResponse createFindReservationByUserResponse() {
        return new FindReservationByUserResponse();
    }

    /**
     * Create an instance of {@link SoapInstanceNotFoundExceptionInfo }
     * 
     */
    public SoapInstanceNotFoundExceptionInfo createSoapInstanceNotFoundExceptionInfo() {
        return new SoapInstanceNotFoundExceptionInfo();
    }

    /**
     * Create an instance of {@link FindOfferResponse }
     * 
     */
    public FindOfferResponse createFindOfferResponse() {
        return new FindOfferResponse();
    }

    /**
     * Create an instance of {@link SoapReservationTimeExpiredExceptionInfo }
     * 
     */
    public SoapReservationTimeExpiredExceptionInfo createSoapReservationTimeExpiredExceptionInfo() {
        return new SoapReservationTimeExpiredExceptionInfo();
    }

    /**
     * Create an instance of {@link OfferInvalidation }
     * 
     */
    public OfferInvalidation createOfferInvalidation() {
        return new OfferInvalidation();
    }

    /**
     * Create an instance of {@link SoapNotModifiableOfferExceptionInfo }
     * 
     */
    public SoapNotModifiableOfferExceptionInfo createSoapNotModifiableOfferExceptionInfo() {
        return new SoapNotModifiableOfferExceptionInfo();
    }

    /**
     * Create an instance of {@link FindOffersResponse }
     * 
     */
    public FindOffersResponse createFindOffersResponse() {
        return new FindOffersResponse();
    }

    /**
     * Create an instance of {@link ClaimOffer }
     * 
     */
    public ClaimOffer createClaimOffer() {
        return new ClaimOffer();
    }

    /**
     * Create an instance of {@link FindOffers }
     * 
     */
    public FindOffers createFindOffers() {
        return new FindOffers();
    }

    /**
     * Create an instance of {@link AddOffer }
     * 
     */
    public AddOffer createAddOffer() {
        return new AddOffer();
    }

    /**
     * Create an instance of {@link FindOffer }
     * 
     */
    public FindOffer createFindOffer() {
        return new FindOffer();
    }

    /**
     * Create an instance of {@link ReserveOffer }
     * 
     */
    public ReserveOffer createReserveOffer() {
        return new ReserveOffer();
    }

    /**
     * Create an instance of {@link RemoveOffer }
     * 
     */
    public RemoveOffer createRemoveOffer() {
        return new RemoveOffer();
    }

    /**
     * Create an instance of {@link OfferInvalidationResponse }
     * 
     */
    public OfferInvalidationResponse createOfferInvalidationResponse() {
        return new OfferInvalidationResponse();
    }

    /**
     * Create an instance of {@link RemoveOfferResponse }
     * 
     */
    public RemoveOfferResponse createRemoveOfferResponse() {
        return new RemoveOfferResponse();
    }

    /**
     * Create an instance of {@link SoapAlreadyInvalidatedExceptionInfo }
     * 
     */
    public SoapAlreadyInvalidatedExceptionInfo createSoapAlreadyInvalidatedExceptionInfo() {
        return new SoapAlreadyInvalidatedExceptionInfo();
    }

    /**
     * Create an instance of {@link AddOfferResponse }
     * 
     */
    public AddOfferResponse createAddOfferResponse() {
        return new AddOfferResponse();
    }

    /**
     * Create an instance of {@link GetUserOffersInfoResponse }
     * 
     */
    public GetUserOffersInfoResponse createGetUserOffersInfoResponse() {
        return new GetUserOffersInfoResponse();
    }

    /**
     * Create an instance of {@link ReserveOfferResponse }
     * 
     */
    public ReserveOfferResponse createReserveOfferResponse() {
        return new ReserveOfferResponse();
    }

    /**
     * Create an instance of {@link UpdateOfferResponse }
     * 
     */
    public UpdateOfferResponse createUpdateOfferResponse() {
        return new UpdateOfferResponse();
    }

    /**
     * Create an instance of {@link UpdateOffer }
     * 
     */
    public UpdateOffer createUpdateOffer() {
        return new UpdateOffer();
    }

    /**
     * Create an instance of {@link SoapAlreadyReservedExceptionInfo }
     * 
     */
    public SoapAlreadyReservedExceptionInfo createSoapAlreadyReservedExceptionInfo() {
        return new SoapAlreadyReservedExceptionInfo();
    }

    /**
     * Create an instance of {@link SoapNotClaimableExceptionInfo }
     * 
     */
    public SoapNotClaimableExceptionInfo createSoapNotClaimableExceptionInfo() {
        return new SoapNotClaimableExceptionInfo();
    }

    /**
     * Create an instance of {@link FindReservationByUser }
     * 
     */
    public FindReservationByUser createFindReservationByUser() {
        return new FindReservationByUser();
    }

    /**
     * Create an instance of {@link GetUserOffersInfo }
     * 
     */
    public GetUserOffersInfo createGetUserOffersInfo() {
        return new GetUserOffersInfo();
    }

    /**
     * Create an instance of {@link Offer }
     * 
     */
    public Offer createOffer() {
        return new Offer();
    }

    /**
     * Create an instance of {@link OfferDto }
     * 
     */
    public OfferDto createOfferDto() {
        return new OfferDto();
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createReservation() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link UserOfferDto }
     * 
     */
    public UserOfferDto createUserOfferDto() {
        return new UserOfferDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindOffers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "findOffers")
    public JAXBElement<FindOffers> createFindOffers(FindOffers value) {
        return new JAXBElement<FindOffers>(_FindOffers_QNAME, FindOffers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddOffer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "addOffer")
    public JAXBElement<AddOffer> createAddOffer(AddOffer value) {
        return new JAXBElement<AddOffer>(_AddOffer_QNAME, AddOffer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindOffer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "findOffer")
    public JAXBElement<FindOffer> createFindOffer(FindOffer value) {
        return new JAXBElement<FindOffer>(_FindOffer_QNAME, FindOffer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveOffer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "reserveOffer")
    public JAXBElement<ReserveOffer> createReserveOffer(ReserveOffer value) {
        return new JAXBElement<ReserveOffer>(_ReserveOffer_QNAME, ReserveOffer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClaimOfferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "claimOfferResponse")
    public JAXBElement<ClaimOfferResponse> createClaimOfferResponse(ClaimOfferResponse value) {
        return new JAXBElement<ClaimOfferResponse>(_ClaimOfferResponse_QNAME, ClaimOfferResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindReservationByUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "findReservationByUserResponse")
    public JAXBElement<FindReservationByUserResponse> createFindReservationByUserResponse(FindReservationByUserResponse value) {
        return new JAXBElement<FindReservationByUserResponse>(_FindReservationByUserResponse_QNAME, FindReservationByUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapInstanceNotFoundExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "SoapInstanceNotFoundException")
    public JAXBElement<SoapInstanceNotFoundExceptionInfo> createSoapInstanceNotFoundException(SoapInstanceNotFoundExceptionInfo value) {
        return new JAXBElement<SoapInstanceNotFoundExceptionInfo>(_SoapInstanceNotFoundException_QNAME, SoapInstanceNotFoundExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindOfferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "findOfferResponse")
    public JAXBElement<FindOfferResponse> createFindOfferResponse(FindOfferResponse value) {
        return new JAXBElement<FindOfferResponse>(_FindOfferResponse_QNAME, FindOfferResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapReservationTimeExpiredExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "SoapReservationTimeExpiredException")
    public JAXBElement<SoapReservationTimeExpiredExceptionInfo> createSoapReservationTimeExpiredException(SoapReservationTimeExpiredExceptionInfo value) {
        return new JAXBElement<SoapReservationTimeExpiredExceptionInfo>(_SoapReservationTimeExpiredException_QNAME, SoapReservationTimeExpiredExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OfferInvalidation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "offerInvalidation")
    public JAXBElement<OfferInvalidation> createOfferInvalidation(OfferInvalidation value) {
        return new JAXBElement<OfferInvalidation>(_OfferInvalidation_QNAME, OfferInvalidation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapNotModifiableOfferExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "SoapNotModifiableOfferException")
    public JAXBElement<SoapNotModifiableOfferExceptionInfo> createSoapNotModifiableOfferException(SoapNotModifiableOfferExceptionInfo value) {
        return new JAXBElement<SoapNotModifiableOfferExceptionInfo>(_SoapNotModifiableOfferException_QNAME, SoapNotModifiableOfferExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindOffersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "findOffersResponse")
    public JAXBElement<FindOffersResponse> createFindOffersResponse(FindOffersResponse value) {
        return new JAXBElement<FindOffersResponse>(_FindOffersResponse_QNAME, FindOffersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClaimOffer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "claimOffer")
    public JAXBElement<ClaimOffer> createClaimOffer(ClaimOffer value) {
        return new JAXBElement<ClaimOffer>(_ClaimOffer_QNAME, ClaimOffer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOffer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "updateOffer")
    public JAXBElement<UpdateOffer> createUpdateOffer(UpdateOffer value) {
        return new JAXBElement<UpdateOffer>(_UpdateOffer_QNAME, UpdateOffer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapAlreadyReservedExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "SoapAlreadyReservedException")
    public JAXBElement<SoapAlreadyReservedExceptionInfo> createSoapAlreadyReservedException(SoapAlreadyReservedExceptionInfo value) {
        return new JAXBElement<SoapAlreadyReservedExceptionInfo>(_SoapAlreadyReservedException_QNAME, SoapAlreadyReservedExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapNotClaimableExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "SoapNotClaimableExceptionInfo")
    public JAXBElement<SoapNotClaimableExceptionInfo> createSoapNotClaimableExceptionInfo(SoapNotClaimableExceptionInfo value) {
        return new JAXBElement<SoapNotClaimableExceptionInfo>(_SoapNotClaimableExceptionInfo_QNAME, SoapNotClaimableExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindReservationByUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "findReservationByUser")
    public JAXBElement<FindReservationByUser> createFindReservationByUser(FindReservationByUser value) {
        return new JAXBElement<FindReservationByUser>(_FindReservationByUser_QNAME, FindReservationByUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserOffersInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "getUserOffersInfo")
    public JAXBElement<GetUserOffersInfo> createGetUserOffersInfo(GetUserOffersInfo value) {
        return new JAXBElement<GetUserOffersInfo>(_GetUserOffersInfo_QNAME, GetUserOffersInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveOffer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "removeOffer")
    public JAXBElement<RemoveOffer> createRemoveOffer(RemoveOffer value) {
        return new JAXBElement<RemoveOffer>(_RemoveOffer_QNAME, RemoveOffer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OfferInvalidationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "offerInvalidationResponse")
    public JAXBElement<OfferInvalidationResponse> createOfferInvalidationResponse(OfferInvalidationResponse value) {
        return new JAXBElement<OfferInvalidationResponse>(_OfferInvalidationResponse_QNAME, OfferInvalidationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveOfferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "removeOfferResponse")
    public JAXBElement<RemoveOfferResponse> createRemoveOfferResponse(RemoveOfferResponse value) {
        return new JAXBElement<RemoveOfferResponse>(_RemoveOfferResponse_QNAME, RemoveOfferResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapAlreadyInvalidatedExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "SoapAlreadyInvalidatedException")
    public JAXBElement<SoapAlreadyInvalidatedExceptionInfo> createSoapAlreadyInvalidatedException(SoapAlreadyInvalidatedExceptionInfo value) {
        return new JAXBElement<SoapAlreadyInvalidatedExceptionInfo>(_SoapAlreadyInvalidatedException_QNAME, SoapAlreadyInvalidatedExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "SoapInputValidationException")
    public JAXBElement<String> createSoapInputValidationException(String value) {
        return new JAXBElement<String>(_SoapInputValidationException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddOfferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "addOfferResponse")
    public JAXBElement<AddOfferResponse> createAddOfferResponse(AddOfferResponse value) {
        return new JAXBElement<AddOfferResponse>(_AddOfferResponse_QNAME, AddOfferResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserOffersInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "getUserOffersInfoResponse")
    public JAXBElement<GetUserOffersInfoResponse> createGetUserOffersInfoResponse(GetUserOffersInfoResponse value) {
        return new JAXBElement<GetUserOffersInfoResponse>(_GetUserOffersInfoResponse_QNAME, GetUserOffersInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveOfferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "reserveOfferResponse")
    public JAXBElement<ReserveOfferResponse> createReserveOfferResponse(ReserveOfferResponse value) {
        return new JAXBElement<ReserveOfferResponse>(_ReserveOfferResponse_QNAME, ReserveOfferResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOfferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.app.udc.es/", name = "updateOfferResponse")
    public JAXBElement<UpdateOfferResponse> createUpdateOfferResponse(UpdateOfferResponse value) {
        return new JAXBElement<UpdateOfferResponse>(_UpdateOfferResponse_QNAME, UpdateOfferResponse.class, null, value);
    }

}
