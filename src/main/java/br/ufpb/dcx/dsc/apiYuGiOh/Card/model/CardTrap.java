package br.ufpb.dcx.dsc.apiYuGiOh.Card.model;

import br.ufpb.dcx.dsc.apiYuGiOh.Card.ENUM.TipoTrap;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_card_trap")
public class CardTrap extends Card{

    @Column(name = "tipo_trap")
    @Enumerated(EnumType.STRING)
    private TipoTrap tipoTrap;

    public CardTrap(){
    }

    public TipoTrap getTipoTrap() {
        return tipoTrap;
    }

    public void setTipoTrap(TipoTrap tipoTrap) {
        this.tipoTrap = tipoTrap;
    }
}
