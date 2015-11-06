package es.udc.ws.app.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class NotClaimableException extends Exception{
    private Long reservationId;
    private Calendar expirationDate;

    public NotClaimableException(Long reservationId, Calendar expirationDate) {
        super("Reservation with id=\"" + reservationId + 
              "\" has expired (expirationDate = \"" + 
              expirationDate + "\")");
        this.reservationId = reservationId;
        this.expirationDate = expirationDate;
    }

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public Calendar getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Calendar expirationDate) {
		this.expirationDate = expirationDate;
	}
    
}
