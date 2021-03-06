package es.udc.ws.app.dto;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReservationDto {

	private String email;
	private long offerId;
	private String state;
	private Calendar requestDate;
	private long reservationId;
	private String creditCardNumber;
	private float reservationPrice;

	public ReservationDto(String email, long offerId, String state,
			Calendar requestDate, String creditCardNumber,
			float reservationPrice) {
		super();
		this.email = email;
		this.offerId = offerId;
		this.state = state;
		this.requestDate = requestDate;
		if (requestDate != null)
			this.requestDate.set(Calendar.MILLISECOND, 0);
		this.creditCardNumber = creditCardNumber;
		this.reservationPrice = reservationPrice;
	}

	public ReservationDto(String email, long offerId, String state,
			Calendar requestDate, long reservationId, String creditCardNumber,
			float reservationPrice) {
		this(email, offerId, state, requestDate, creditCardNumber,
				reservationPrice);
		this.reservationId = reservationId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Calendar getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Calendar requestDate) {
		this.requestDate = requestDate;
		if (requestDate != null)
			this.requestDate.set(Calendar.MILLISECOND, 0);
	}

	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public float getReservationPrice() {
		return reservationPrice;
	}

	public void setReservationPrice(float reservationPrice) {
		this.reservationPrice = reservationPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (offerId ^ (offerId >>> 32));
		result = prime * result
				+ ((requestDate == null) ? 0 : requestDate.hashCode());
		result = prime * result
				+ (int) (reservationId ^ (reservationId >>> 32));
		result = prime * result + Float.floatToIntBits(reservationPrice);
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservationDto other = (ReservationDto) obj;
		if (creditCardNumber == null) {
			if (other.creditCardNumber != null)
				return false;
		} else if (!creditCardNumber.equals(other.creditCardNumber))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (offerId != other.offerId)
			return false;
		if (requestDate == null) {
			if (other.requestDate != null)
				return false;
		} else if (!requestDate.equals(other.requestDate))
			return false;
		if (reservationId != other.reservationId)
			return false;
		if (Float.floatToIntBits(reservationPrice) != Float
				.floatToIntBits(other.reservationPrice))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm");
		return "ReservationDto [email=" + email + ", offerId=" + offerId
				+ ", state=" + state + ", requestDate="
				+ formatter.format(requestDate.getTime()) + ", reservationId="
				+ reservationId + ", creditCardNumber=" + creditCardNumber
				+ ", reservationPrice=" + reservationPrice + "]";
	}

}
