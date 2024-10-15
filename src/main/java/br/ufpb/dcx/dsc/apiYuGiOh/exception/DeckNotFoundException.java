package br.ufpb.dcx.dsc.apiYuGiOh.exception;

public class DeckNotFoundException extends RuntimeException {

    public DeckNotFoundException(String message) {
        super(message);
    }

    public DeckNotFoundException() {
        super("Deck não encontrado!");
    }
}
