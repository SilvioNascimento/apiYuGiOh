package br.ufpb.dcx.dsc.apiYuGiOh.Card.model;

import br.ufpb.dcx.dsc.apiYuGiOh.Card.ENUM.TipoSpell;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_card_spell")
public class CardSpell extends Card{

    @Column(name = "tipo_spell")
    @Enumerated(EnumType.STRING)
    private TipoSpell tipoSpell;

    public CardSpell() {
    }

    public TipoSpell getTipoSpell() {
        return tipoSpell;
    }

    public void setTipoSpell(TipoSpell tipoSpell) {
        this.tipoSpell = tipoSpell;
    }
}
