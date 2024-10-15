package br.ufpb.dcx.dsc.apiYuGiOh.exception;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(String msg) {
        super(msg);
    }

    public CardNotFoundException() {
        super("Card não encontrado!");
    }
}
