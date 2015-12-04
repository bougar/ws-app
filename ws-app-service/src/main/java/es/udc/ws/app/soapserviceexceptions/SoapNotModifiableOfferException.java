package es.udc.ws.app.soapserviceexceptions;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
	    name="SoapNotModifiableOfferException",
	    targetNamespace="http://soap.ws.udc.es/"
	)
public class SoapNotModifiableOfferException extends Exception {
	private SoapNotModifiableOfferExceptionInfo faultInfo;
	
	public SoapNotModifiableOfferException(SoapNotModifiableOfferExceptionInfo faultInfo){
		this.faultInfo=faultInfo;
	}
	
	public SoapNotModifiableOfferExceptionInfo getFaultInfo(){
		return faultInfo;
	}
}
