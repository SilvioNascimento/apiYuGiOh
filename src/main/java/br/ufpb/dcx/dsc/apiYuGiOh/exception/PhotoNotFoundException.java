package br.ufpb.dcx.dsc.apiYuGiOh.exception;

public class PhotoNotFoundException extends RuntimeException {

    public PhotoNotFoundException() {
        super("Photo não encontrado!");
    }

    public PhotoNotFoundException(String message) {
        super(message);
    }
}
