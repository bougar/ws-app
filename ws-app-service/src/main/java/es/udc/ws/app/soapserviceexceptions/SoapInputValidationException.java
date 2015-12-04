package es.udc.ws.app.soapserviceexceptions;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapInputValidationException",
    targetNamespace="http://soap.ws.udc.es/offer"
)
public class SoapInputValidationException extends Exception {

	public SoapInputValidationException(String message) {
		super(message);
	}

	public String getFaultInfo() {
		return getMessage();
	}
}
