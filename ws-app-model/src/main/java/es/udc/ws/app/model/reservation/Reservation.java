package es.udc.ws.app.model.reservation;
import java.util.Calendar;

public class Reservation {

	private String email;
	private long offerId;
	private EnumState state;
	private Calendar requestDate;
	private long reservationId;
	private String creditCardNumber;
	
	public Reservation(String email, long offerId, EnumState state,
			Calendar requestDate, long reservationId, String creditCardNumber) {
		super();
		this.email = email;
		this.offerId = offerId;
		this.state = state;
		this.requestDate = requestDate;
		if (requestDate != null)
			this.requestDate.set(Calendar.MILLISECOND,0);
		this.reservationId = reservationId;
		this.creditCardNumber = creditCardNumber;
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

	public EnumState getState() {
		return state;
	}

	public void setState(EnumState state) {
		this.state = state;
	}

	public Calendar getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Calendar requestDate) {
		this.requestDate = requestDate;
		if (requestDate != null)
			this.requestDate.set(Calendar.MILLISECOND,0);
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
		Reservation other = (Reservation) obj;
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
		if (state != other.state)
			return false;
		return true;
	}
	
	
	
}
