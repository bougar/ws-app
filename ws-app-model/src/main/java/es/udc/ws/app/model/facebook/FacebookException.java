package es.udc.ws.app.model.facebook;

@SuppressWarnings("serial")
public class FacebookException extends Exception {
	private int errorCode;
	private String facebookError;

	public FacebookException(int errorCode, String facebookError) {
		super("Facebook Exception");
		this.errorCode = errorCode;
		this.facebookError = facebookError;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getFacebookError() {
		return facebookError;
	}

	public void setFacebookError(String facebookError) {
		this.facebookError = facebookError;
	}

}
