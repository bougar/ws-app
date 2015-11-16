package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class ReservationTimeExpiredException extends Exception{

	private Long offerId;
	
    public ReservationTimeExpiredException(Long offerId) {
        super("Offer with id=\"" + offerId + 
              "\" is lo longer reservable "+ "\")");
        this.offerId = offerId;
    }

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
    
    
}
