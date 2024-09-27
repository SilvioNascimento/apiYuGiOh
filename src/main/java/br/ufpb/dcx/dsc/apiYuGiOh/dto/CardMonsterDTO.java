package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoMonster;

import java.util.List;

public class CardMonsterDTO extends CardDTO{
    private int nivel;
    private int atk;
    private int def;
    private String atributo;
    private List<TipoMonster> tipoMonster;

    public CardMonsterDTO() {
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

    public void setTipoMonster(List<TipoMonster> tipoMonster) {
        this.tipoMonster = tipoMonster;
    }
}
