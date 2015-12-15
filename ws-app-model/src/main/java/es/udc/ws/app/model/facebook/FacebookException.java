package es.udc.ws.app.model.facebook;
/*Exception for unexpected errors (parsing, getStrings, ...)*/
@SuppressWarnings("serial")
public class FacebookException extends Exception {
	public FacebookException(Exception e){
		super (e);
	}
}
