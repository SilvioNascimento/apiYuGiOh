package br.ufpb.dcx.dsc.apiYuGiOh.Deck.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_deck")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "deck_id")
    private Long id;

    @Column(name = "nome")
    private String nomeDeck;

    public Deck(){
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
