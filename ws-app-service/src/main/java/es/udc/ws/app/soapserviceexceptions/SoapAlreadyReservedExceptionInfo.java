package es.udc.ws.app.soapserviceexceptions;

public class SoapAlreadyReservedExceptionInfo {
	private Long offerId;
	private String user;

	public SoapAlreadyReservedExceptionInfo() {
	}

	public SoapAlreadyReservedExceptionInfo(Long offerId, String user) {
		this.offerId = offerId;
		this.user = user;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
