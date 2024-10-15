package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoTrap;
import br.ufpb.dcx.dsc.apiYuGiOh.validation.TipoTrapExistente;
import com.fasterxml.jackson.annotation.JsonAlias;

public class CardTrapDTO extends CardDTO{
    @JsonAlias({"tipotrap", "tipo_trap"})
    @TipoTrapExistente(enumClass = TipoTrap.class)
    private TipoTrap tipoTrap;

    public CardTrapDTO(){}

    public TipoTrap getTipoTrap() {
        return tipoTrap;
    }

    public void setTipoTrap(TipoTrap tipoTrap) {
        this.tipoTrap = tipoTrap;
    }
}
