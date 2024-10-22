package br.ufpb.dcx.dsc.apiYuGiOh.model;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoMonster;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_card_monster")
public class CardMonster extends Card{

    @Column(name = "nivel")
    private int nivel;

    @Column(name = "atk")
    private int atk;

    @Column(name = "def")
    private int def;

    @Column(name = "atributo")
    private String atributo;

    @ElementCollection(fetch = FetchType.EAGER)  // FetchType.EAGER para carregar a lista junto com a entidade principal
    @Enumerated(EnumType.STRING)  // Armazena o nome da enumeração como string no banco de dados
    @Column(name = "tipo_monster")
    private List<TipoMonster> tipoMonster;

    public CardMonster(){
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public List<TipoMonster> getTipoMonster() {
        return tipoMonster;
    }

    // Fazer um controle de que apenas aceita 2 tipos, no máximo
    public void setTipoMonster(List<TipoMonster> tipoMonster) {
        this.tipoMonster = tipoMonster;
    }
}
