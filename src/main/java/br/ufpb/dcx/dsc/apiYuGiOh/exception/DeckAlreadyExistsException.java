package br.ufpb.dcx.dsc.apiYuGiOh.exception;

public class DeckAlreadyExistsException extends RuntimeException{
    public DeckAlreadyExistsException() {
        super("Deck já existe!");
    }

    public DeckAlreadyExistsException(String message) {
        super(message);
    }
}
