package es.udc.ws.app.soapserviceexceptions;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapInstanceNotFoundException",
    targetNamespace="http://soap.ws.app.udc.es/"
)
public class SoapInstanceNotFoundException extends Exception {

	private SoapInstanceNotFoundExceptionInfo faultInfo;

	public SoapInstanceNotFoundException(
			SoapInstanceNotFoundExceptionInfo faultInfo) {
		this.faultInfo = faultInfo;
	}

	public SoapInstanceNotFoundExceptionInfo getFaultInfo() {
		return faultInfo;
	}
}
