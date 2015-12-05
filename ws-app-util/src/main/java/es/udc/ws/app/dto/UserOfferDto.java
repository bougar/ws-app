package es.udc.ws.app.dto;

import java.util.Calendar;

public class UserOfferDto {
	private String description;
	private float discountedPrice;
	private Calendar reservationDate;
	
	
	public UserOfferDto(String description, float discountedPrice,
			Calendar reservationDate) {
		super();
		this.description = description;
		this.discountedPrice = discountedPrice;
		this.reservationDate = reservationDate;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(float discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public Calendar getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Calendar reservationDate) {
		this.reservationDate = reservationDate;
	}
	
	
	
}
