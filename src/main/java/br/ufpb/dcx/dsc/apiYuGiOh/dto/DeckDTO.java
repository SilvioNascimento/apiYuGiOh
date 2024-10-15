package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class DeckDTO {

    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
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
