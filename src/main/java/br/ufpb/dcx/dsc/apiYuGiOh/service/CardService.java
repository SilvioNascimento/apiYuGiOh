package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.exception.CardNotFoundException;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Card;
import br.ufpb.dcx.dsc.apiYuGiOh.model.CardMonster;
import br.ufpb.dcx.dsc.apiYuGiOh.model.CardSpell;
import br.ufpb.dcx.dsc.apiYuGiOh.model.CardTrap;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.CardRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card getCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException("Card do id " + id +
                " não foi encontrado!"));
    }

    public List<Card> listCards() {
        return cardRepository.findAll();
    }

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

    public Card updateCard(Long id, Card card) {
        Optional<Card> cardOpt = cardRepository.findById(id);
        if (cardOpt.isPresent()) {
            Card toUpdate = cardOpt.get();
            toUpdate.setNome(card.getNome());
            toUpdate.setDescricao(card.getDescricao());
            toUpdate.setPhoto(card.getPhoto());
            if(toUpdate instanceof CardMonster && card instanceof CardMonster) {
                CardMonster cardMonster = (CardMonster) toUpdate;
                CardMonster toUpdatedMonsterCard = (CardMonster) card;
                cardMonster.setAtk(toUpdatedMonsterCard.getAtk());
                cardMonster.setDef(toUpdatedMonsterCard.getDef());
                cardMonster.setNivel(toUpdatedMonsterCard.getNivel());
                cardMonster.setAtributo(toUpdatedMonsterCard.getAtributo());
                cardMonster.setTipoMonster(toUpdatedMonsterCard.getTipoMonster());
            } else if (toUpdate instanceof CardSpell && card instanceof CardSpell) {
                CardSpell spellCard = (CardSpell) toUpdate;
                CardSpell updatedSpellCard = (CardSpell) card;
                spellCard.setTipoSpell(updatedSpellCard.getTipoSpell());
            } else if (toUpdate instanceof CardTrap && card instanceof CardTrap) {
                CardTrap trapCard = (CardTrap) toUpdate;
                CardTrap updatedTrapCard = (CardTrap) card;
                trapCard.setTipoTrap(updatedTrapCard.getTipoTrap());
            }
            return cardRepository.save(toUpdate);
        }
        throw new CardNotFoundException("Card do id " + id + " não foi encontrado!");
    }
}
