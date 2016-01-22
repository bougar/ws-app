package es.udc.ws.app.model.offer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Offer {

	@Override
	public String toString() {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm");
		String str = "name=" + name + "%ndescription=" + description
				+ "%nrealPrice=" + realPrice + "%ndiscountedPrice="
				+ discountedPrice + "%nlimitApplicationDate="
				+ format.format(limitApplicationDate.getTime())
				+ "%nlimitReservationDate="
				+ format.format(limitReservationDate.getTime());
		return String.format(str);
	}

	private String name;
	private String description;
	private Calendar limitReservationDate;
	private Calendar limitApplicationDate;
	private float realPrice;
	private float discountedPrice;
	private float fee;
	private long offerId;
	private boolean isValid;
	private String faceBookId;

	public String getFaceBookId() {
		return faceBookId;
	}

	public void setFaceBookId(String faceBookId) {
		this.faceBookId = faceBookId;
	}

	public Offer() {

	}

	public Offer(String name, String description,
			Calendar limitReservationDate, Calendar limitApplicationDate,
			float realPrice, float discountedPrice, float fee, boolean isValid) {
		super();
		this.name = name;
		this.description = description;
		this.limitReservationDate = limitReservationDate;
		if (limitReservationDate != null)
			this.limitReservationDate.set(Calendar.MILLISECOND, 0);
		this.limitApplicationDate = limitApplicationDate;
		if (limitApplicationDate != null)
			this.limitApplicationDate.set(Calendar.MILLISECOND, 0);
		this.realPrice = realPrice;
		this.discountedPrice = discountedPrice;
		this.fee = fee;
		this.isValid = isValid;
	}

	public Offer(long offerId, String name, String description,
			Calendar limitReservationDate, Calendar limitApplicationDate,
			float realPrice, float discountedPrice, float fee, boolean isValid,
			String faceBookId) {
		this(name, description, limitReservationDate, limitApplicationDate,
				realPrice, discountedPrice, fee, isValid);
		this.offerId = offerId;
		this.faceBookId = faceBookId;
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
			this.limitReservationDate.set(Calendar.MILLISECOND, 0);
	}

	public Calendar getLimitApplicationDate() {
		return limitApplicationDate;
	}

	public void setLimitApplicationDate(Calendar limitApplicationDate) {
		this.limitApplicationDate = limitApplicationDate;
		if (limitApplicationDate != null)
			this.limitApplicationDate.set(Calendar.MILLISECOND, 0);
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
		result = prime * result
				+ ((faceBookId == null) ? 0 : faceBookId.hashCode());
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
		if (faceBookId == null) {
			if (other.faceBookId != null)
				return false;
		} else if (!faceBookId.equals(other.faceBookId))
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

	public boolean equals(ReturnedOffer returnedOffer) {
		if (returnedOffer == null)
			return false;
		if (description == null) {
			if (returnedOffer.getDescription() != null)
				return false;
		} else if (!description.equals(returnedOffer.getDescription()))
			return false;
		if (Float.floatToIntBits(discountedPrice) != Float
				.floatToIntBits(returnedOffer.getDiscountedPrice()))
			return false;
		if (Float.floatToIntBits(fee) != Float.floatToIntBits(returnedOffer
				.getFee()))
			return false;
		if (isValid != returnedOffer.isValid())
			return false;
		if (limitApplicationDate == null) {
			if (returnedOffer.getLimitApplicationDate() != null)
				return false;
		} else if (!limitApplicationDate.equals(returnedOffer
				.getLimitApplicationDate()))
			return false;
		if (limitReservationDate == null) {
			if (returnedOffer.getLimitReservationDate() != null)
				return false;
		} else if (!limitReservationDate.equals(returnedOffer
				.getLimitReservationDate()))
			return false;
		if (name == null) {
			if (returnedOffer.getName() != null)
				return false;
		} else if (!name.equals(returnedOffer.getName()))
			return false;
		if (offerId != returnedOffer.getOfferId())
			return false;
		if (Float.floatToIntBits(realPrice) != Float
				.floatToIntBits(returnedOffer.getRealPrice()))
			return false;
		return true;
	}

}
