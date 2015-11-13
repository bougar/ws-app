package es.udc.ws.app.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class NotClaimableException extends Exception{
    private Long reservationId;
    private Calendar expirationDate;
    private String email;
    private String state;

    /*Reservation application expired*/
    public NotClaimableException(Long reservationId, Calendar expirationDate) {
        super("Reservation with id=\"" + reservationId + 
              "\" has expired (expirationDate = \"" + 
              expirationDate + "\")");
        this.reservationId = reservationId;
        this.expirationDate = expirationDate;
    }
    
    /*User is not owner of reservation*/
    public NotClaimableException(String email) {
        super("User with email =\"" + email + 
              "\" is not authorized to claim this offer");
        this.email = email;
    }

    /*State is not NOT_CLAIMED*/
    public NotClaimableException(Long reservationId, String state) {
        super("Reservation with id=\"" + reservationId + 
              "\" cannot be claimed, its status is (state= \"" + 
              state + "\")");
        this.reservationId = reservationId;
        this.state = state;
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
    
	public String getEmail() {
		return email;
	}
	
	public String getState() {
		return state;
	}
	
}
