package es.udc.ws.app.soapserviceexceptions;

public class SoapNotModifiableOfferExceptionInfo {
	private long offerId;
	
	public SoapNotModifiableOfferExceptionInfo(){
	}
	
	public SoapNotModifiableOfferExceptionInfo(long offerId){
		this.offerId = offerId;
	}

	public void setOfferId(long offerId){
		this.offerId = offerId;
	}
	
	public long getOfferId(){
		return offerId;
	}
	
}
