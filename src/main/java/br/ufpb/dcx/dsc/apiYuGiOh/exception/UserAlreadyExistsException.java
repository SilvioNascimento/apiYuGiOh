package br.ufpb.dcx.dsc.apiYuGiOh.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User já existe!");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
