package es.udc.ws.app.soapserviceexceptions;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
	    name="SoapNotClaimableExceptionInfo",
	    targetNamespace="http://soap.ws.app.udc.es/"
	)
public class SoapNotClaimableException extends Exception {
	private SoapNotClaimableExceptionInfo faultInfo;

	public SoapNotClaimableException(SoapNotClaimableExceptionInfo faultInfo) {
		this.faultInfo = faultInfo;
	}

	public SoapNotClaimableExceptionInfo getFaultInfo() {
		return faultInfo;
	}

}
