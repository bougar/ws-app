package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class AlreadyInvalidatedException extends Exception{

	private Long offerId;
	
    public AlreadyInvalidatedException(Long offerId) {
        super("Offer with id=\"" + offerId + 
              "\" has already been invalidated");
        this.offerId = offerId;
    }

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
    
    
}

