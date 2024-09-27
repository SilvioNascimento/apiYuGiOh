package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoTrap;

public class CardTrapDTO extends CardDTO{
    private TipoTrap tipoTrap;

    public CardTrapDTO(){}

    public TipoTrap getTipoTrap() {
        return tipoTrap;
    }

    public void setTipoTrap(TipoTrap tipoTrap) {
        this.tipoTrap = tipoTrap;
    }
}
