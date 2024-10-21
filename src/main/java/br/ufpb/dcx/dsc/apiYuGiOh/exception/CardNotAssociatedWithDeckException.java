package br.ufpb.dcx.dsc.apiYuGiOh.exception;

public class CardNotAssociatedWithDeckException extends RuntimeException{
    public CardNotAssociatedWithDeckException() {
    }

    public CardNotAssociatedWithDeckException(String message) {
        super(message);
    }
}
