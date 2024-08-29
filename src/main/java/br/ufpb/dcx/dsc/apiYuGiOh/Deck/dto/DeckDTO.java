package br.ufpb.dcx.dsc.apiYuGiOh.Deck.dto;

public class DeckDTO {

    private Long id;
    private String nomeDeck;

    public DeckDTO(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDeck() {
        return nomeDeck;
    }

    public void setNomeDeck(String nomeDeck) {
        this.nomeDeck = nomeDeck;
    }
}
