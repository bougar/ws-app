package es.udc.ws.app.soapserviceexceptions;

public class SoapAlreadyInvalidatedExceptionInfo {
	private long offerId;

	public SoapAlreadyInvalidatedExceptionInfo() {
	}

	public SoapAlreadyInvalidatedExceptionInfo(long offerId) {
		this.offerId = offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public long getOfferId() {
		return offerId;
	}

}
