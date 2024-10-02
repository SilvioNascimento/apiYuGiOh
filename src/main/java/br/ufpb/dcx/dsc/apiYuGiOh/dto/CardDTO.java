package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;



@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CardMonsterDTO.class, name = "monster"),
        @JsonSubTypes.Type(value = CardSpellDTO.class, name = "spell"),
        @JsonSubTypes.Type(value = CardTrapDTO.class, name = "trap")
})

public abstract class CardDTO {
    private Long id;
    private String nome;
    private String descricao;

    public CardDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
