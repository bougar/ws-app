package es.udc.ws.app.soapserviceexceptions;

public class SoapReservationTimeExpiredExceptionInfo {
	private long offerId;
	private String user;

	public SoapReservationTimeExpiredExceptionInfo() {
	}

	public SoapReservationTimeExpiredExceptionInfo(long offerId, String user) {
		this.offerId = offerId;
		this.user = user;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
