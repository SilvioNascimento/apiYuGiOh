package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoSpell;
import br.ufpb.dcx.dsc.apiYuGiOh.validation.TipoSpellExistente;
import com.fasterxml.jackson.annotation.JsonAlias;

public class CardSpellDTO extends CardDTO{
    @JsonAlias({"tipospell", "tipo_spell"})
    @TipoSpellExistente(enumClass = TipoSpell.class)
    private TipoSpell tipoSpell;

    public CardSpellDTO(){}

    public TipoSpell getTipoSpell() {
        return tipoSpell;
    }

    public void setTipoSpell(TipoSpell tipospell) {
        this.tipoSpell = tipospell;
    }
}
