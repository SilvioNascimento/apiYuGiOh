package br.ufpb.dcx.dsc.apiYuGiOh.model;

import br.ufpb.dcx.dsc.apiYuGiOh.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_deck")
@JsonIgnoreProperties({"user"})
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "deck_id")
    private Long id;

    @Column(name = "nome")
    private String nomeDeck;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "deck_cards",
    joinColumns = @JoinColumn(name = "deck_id"),
    inverseJoinColumns = @JoinColumn(name = "card_id"))
    private List<Card> cards;

    public Deck(){
        this.cards = new ArrayList<>();
    }

    public Deck(String nomeDeck) {
        this.nomeDeck = nomeDeck;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
