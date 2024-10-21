package br.ufpb.dcx.dsc.apiYuGiOh.exception;

public class PhotoAlreadyExistsException extends RuntimeException{
    public PhotoAlreadyExistsException() {
        super("Photo já existe!");
    }

    public PhotoAlreadyExistsException(String message) {
        super(message);
    }
}
