package br.ufpb.dcx.dsc.apiYuGiOh.exception;

public class PhotoNotAssociatedWithCardException extends RuntimeException{
    public PhotoNotAssociatedWithCardException() {
    }

    public PhotoNotAssociatedWithCardException(String message) {
        super(message);
    }
}
