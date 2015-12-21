package es.udc.ws.app.exceptions;

/*Facebook request work but it fail.*/
@SuppressWarnings("serial")
public class HttpFacebookException extends Exception {
	private Integer errorCode;
	private String facebookError;

	public HttpFacebookException(int errorCode, String facebookError) {
		super(facebookError);
		this.errorCode = errorCode;
		this.facebookError = facebookError;
	}

	public Integer getErrorCode() {
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

	public String getMessage() {
		return "InternalError: facebook request errorCode:" + errorCode
				+ " with message " + facebookError;
	}

}
