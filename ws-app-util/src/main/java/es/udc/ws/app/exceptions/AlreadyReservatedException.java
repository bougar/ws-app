package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class AlreadyReservatedException extends Exception {
	private Long offerId;
	private String user;
    public AlreadyReservatedException(Long offerId,String user) {
        super("Offer with id=\"" + offerId + 
              "\" is already reserved by user=\""+user+ "\")");
        this.offerId = offerId;
        this.user=user;
    }
    public String getUser(){
    	return user;
    }
    
    public void setUser(String user){
    	this.user=user;
    }
    
	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

}
