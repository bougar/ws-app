package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class InputValidationException extends Exception {

    public InputValidationException(String message) {
        super(message);
    }
}
