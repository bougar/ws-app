package es.udc.ws.app.soapserviceexceptions;

import java.util.Calendar;

public class SoapNotClaimableExceptionInfo {
    private Long reservationId;
    private Calendar expirationDate;
    private String email;
    private String state;
    
    public SoapNotClaimableExceptionInfo(){
    }

	public SoapNotClaimableExceptionInfo(Long reservationId,
			Calendar expirationDate, String email, String state) {
		this.reservationId = reservationId;
		this.expirationDate = expirationDate;
		this.email = email;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
