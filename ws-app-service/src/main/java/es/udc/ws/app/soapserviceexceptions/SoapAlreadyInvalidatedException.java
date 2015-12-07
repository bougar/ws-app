package es.udc.ws.app.soapserviceexceptions;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapAlreadyInvalidatedException",
    targetNamespace="http://soap.ws.app.udc.es/"
)
public class SoapAlreadyInvalidatedException extends Exception {
	private SoapAlreadyInvalidatedExceptionInfo fautlInfo;

	public SoapAlreadyInvalidatedException(
			SoapAlreadyInvalidatedExceptionInfo faultInfo) {
		this.fautlInfo = faultInfo;
	}

	public SoapAlreadyInvalidatedExceptionInfo getFaultInfo() {
		return this.fautlInfo;
	}
}
