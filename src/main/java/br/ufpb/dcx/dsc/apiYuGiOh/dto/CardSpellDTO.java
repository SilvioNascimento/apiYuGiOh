package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoSpell;

public class CardSpellDTO extends CardDTO{
    private TipoSpell tipoSpell;

    public CardSpellDTO(){}

    public TipoSpell getTipoSpell() {
        return tipoSpell;
    }

    public void setTipoSpell(TipoSpell tipoSpell) {
        this.tipoSpell = tipoSpell;
    }
}
