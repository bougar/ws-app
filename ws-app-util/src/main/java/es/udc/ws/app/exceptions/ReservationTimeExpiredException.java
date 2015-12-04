package es.udc.ws.app.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class ReservationTimeExpiredException extends Exception{

	private Long offerId;
	private Calendar limitReservationDate;
	
	public ReservationTimeExpiredException(Long offerId,
			Calendar limitReservationDate) {
		super("Offer with id=\"" + offerId + "\" is lo longer reservable "
				+ "\")");
		this.offerId = offerId;
		this.limitReservationDate = limitReservationDate;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Calendar getLimitReservationDate() {
		return limitReservationDate;
	}

	public void setLimitReservationDate(Calendar limitReservationDate) {
		this.limitReservationDate = limitReservationDate;
	}
	
	
    
    
}
