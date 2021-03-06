package es.udc.ws.app.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OfferDto {
	private String name;
	private String description;
	private Calendar limitReservationDate;
	private Calendar limitApplicationDate;
	private float realPrice;
	private float discountedPrice;
	private long offerId;
	private boolean isValid;
	private Long likes;

	public OfferDto(long offerId, String name, String description,
			Calendar limitReservationDate, Calendar limitApplicationDate,
			float realPrice, float discountedPrice, boolean isValid, Long likes) {
		super();
		this.offerId = offerId;
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
		this.isValid = isValid;
		this.offerId = offerId;
		this.likes = likes;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
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
	public String toString() {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm");
		return "OfferDto [name=" + name + ", description=" + description
				+ ", limitReservationDate="
				+ formatter.format(limitReservationDate.getTime())
				+ ", limitApplicationDate="
				+ formatter.format(limitApplicationDate.getTime())
				+ ", realPrice=" + realPrice + ", discountedPrice="
				+ discountedPrice + ", offerId=" + offerId + ", isValid="
				+ isValid + ", likes=" + likes + "]";
	}
}
