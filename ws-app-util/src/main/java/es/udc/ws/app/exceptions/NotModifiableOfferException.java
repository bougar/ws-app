package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class NotModifiableOfferException extends Exception{

	private Long offerId;
	
    public NotModifiableOfferException(Long offerId) {
        super("Offer with id=\"" + offerId + 
              "\" cannot be modified "+ "\")");
        this.offerId = offerId;
    }

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
    
}
