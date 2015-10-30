package es.udc.ws.app.model;
import java.util.Calendar;

public class Offer {
	private String name;
	private String description;
	private Calendar limitReservationDate;
	private Calendar limitApplicationDate;
	private float realPrice;
	private float discountedPrice;
	private float fee;
	private long offerId;
	private boolean isValid;
	
	public Offer(String name, String description,
			Calendar limitReservationDate, Calendar limitApplicationDate,
			float realPrice, float discountedPrice, float fee,
			boolean isValid) {
		super();
		this.name = name;
		this.description = description;
		this.limitReservationDate = limitReservationDate;
		if (limitReservationDate != null)
				this.limitReservationDate.set(Calendar.MILLISECOND,0);
		this.limitApplicationDate = limitApplicationDate;
		if (limitApplicationDate != null)
			this.limitReservationDate.set(Calendar.MILLISECOND,0);
		this.realPrice = realPrice;
		this.discountedPrice = discountedPrice;
		this.fee = fee;
		this.isValid = isValid;
	}
	
	public Offer(long offerId,String name, String description,
			Calendar limitReservationDate, Calendar limitApplicationDate,
			float realPrice, float discountedPrice, float fee,
			boolean isValid) {
		this (name,description,limitReservationDate,limitApplicationDate,
				realPrice,discountedPrice,fee,isValid);
		this.offerId=offerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getLimitReservationDate() {
		return limitReservationDate;
	}

	public void setLimitReservationDate(Calendar limitReservationDate) {
		this.limitReservationDate = limitReservationDate;
		if (limitReservationDate != null)
			this.limitReservationDate.set(Calendar.MILLISECOND,0);
	}

	public Calendar getLimitApplicationDate() {
		return limitApplicationDate;
	}

	public void setLimitApplicationDate(Calendar limitApplicationDate) {
		this.limitApplicationDate = limitApplicationDate;
		if (limitApplicationDate != null)
			this.limitReservationDate.set(Calendar.MILLISECOND,0);
	}

	public float getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(float realPrice) {
		this.realPrice = realPrice;
	}

	public float getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(float discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + Float.floatToIntBits(discountedPrice);
		result = prime * result + Float.floatToIntBits(fee);
		result = prime * result + (isValid ? 1231 : 1237);
		result = prime
				* result
				+ ((limitApplicationDate == null) ? 0 : limitApplicationDate
						.hashCode());
		result = prime
				* result
				+ ((limitReservationDate == null) ? 0 : limitReservationDate
						.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (offerId ^ (offerId >>> 32));
		result = prime * result + Float.floatToIntBits(realPrice);
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
		Offer other = (Offer) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Float.floatToIntBits(discountedPrice) != Float
				.floatToIntBits(other.discountedPrice))
			return false;
		if (Float.floatToIntBits(fee) != Float.floatToIntBits(other.fee))
			return false;
		if (isValid != other.isValid)
			return false;
		if (limitApplicationDate == null) {
			if (other.limitApplicationDate != null)
				return false;
		} else if (!limitApplicationDate.equals(other.limitApplicationDate))
			return false;
		if (limitReservationDate == null) {
			if (other.limitReservationDate != null)
				return false;
		} else if (!limitReservationDate.equals(other.limitReservationDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (offerId != other.offerId)
			return false;
		if (Float.floatToIntBits(realPrice) != Float
				.floatToIntBits(other.realPrice))
			return false;
		return true;
	}
	
	
	
	
}
