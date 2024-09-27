package br.ufpb.dcx.dsc.apiYuGiOh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_card")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @OneToOne
    @JoinColumn(name="photo_id")
    private Photo photo;

    public Card(){
    }

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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
