package es.udc.ws.app.soapserviceexceptions;

import java.util.Calendar;

public class SoapReservationTimeExpiredExceptionInfo {
	private long offerId;
	private Calendar limitReservationDate;

	public SoapReservationTimeExpiredExceptionInfo() {
	}

	public SoapReservationTimeExpiredExceptionInfo(long offerId, Calendar limitReservationDate) {
		this.offerId = offerId;
		this.limitReservationDate = limitReservationDate;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public Calendar getLimitReservationDate() {
		return limitReservationDate;
	}

	public void setLimitReservationDate(Calendar limitReservationDate) {
		this.limitReservationDate = limitReservationDate;
	}

}
