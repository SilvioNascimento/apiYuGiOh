package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoMonster;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

public class CardMonsterDTO extends CardDTO{
    @Min(value = 1)
    @Max(value = 12)
    private int nivel;

    @Min(value = 0)
    private int atk;

    @Min(value = 0)
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
