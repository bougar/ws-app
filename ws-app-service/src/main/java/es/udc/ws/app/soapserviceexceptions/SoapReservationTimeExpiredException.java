package es.udc.ws.app.soapserviceexceptions;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
	    name="SoapReservationTimeExpiredException",
	    targetNamespace="http://soap.ws.app.udc.es/"
	)
public class SoapReservationTimeExpiredException extends Exception {
	private SoapReservationTimeExpiredExceptionInfo faultInfo;

	public SoapReservationTimeExpiredException(
			SoapReservationTimeExpiredExceptionInfo faultInfo) {
		this.faultInfo = faultInfo;
	}

	public SoapReservationTimeExpiredExceptionInfo getFaultInfo() {
		return faultInfo;
	}
}
