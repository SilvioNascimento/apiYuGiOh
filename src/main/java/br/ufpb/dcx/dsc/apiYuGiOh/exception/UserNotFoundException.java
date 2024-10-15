package br.ufpb.dcx.dsc.apiYuGiOh.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("User não encontrado!");
    }
}
