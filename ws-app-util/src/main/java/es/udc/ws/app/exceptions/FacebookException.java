package es.udc.ws.app.exceptions;
/*Exception for unexpected errors (parsing, getStrings, ...)*/
@SuppressWarnings("serial")
public class FacebookException extends Exception {
	public FacebookException(Exception e){
		super (e);
	}
}
