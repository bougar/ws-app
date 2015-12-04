package es.udc.ws.app.soapserviceexceptions;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapAlreadyReservedException",
    targetNamespace="http://soap.ws.udc.es/offer"
)
public class SoapAlreadyReservedException extends Exception {
	private SoapAlreadyReservedExceptionInfo faultInfo;

	public SoapAlreadyReservedException(
			SoapAlreadyReservedExceptionInfo faultInfo) {
		this.faultInfo = faultInfo;
	}

	public SoapAlreadyReservedExceptionInfo getFaultInfo() {
		return this.faultInfo;
	}
}
