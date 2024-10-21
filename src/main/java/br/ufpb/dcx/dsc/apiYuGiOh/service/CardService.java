package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.exception.CardNotFoundException;
import br.ufpb.dcx.dsc.apiYuGiOh.model.*;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.CardRepository;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.PhotoRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private CardRepository cardRepository;
    private PhotoRepository photoRepository;

    public CardService(CardRepository cardRepository, PhotoRepository photoRepository) {
        this.cardRepository = cardRepository;
        this.photoRepository = photoRepository;
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
        Optional<Card> cardOpt = cardRepository.findById(id);
        if(cardOpt.isPresent()){
            cardRepository.deleteById(id);
        }

        throw new CardNotFoundException("Card do id " + id + " não foi encontrado para ser deletado!");
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
        throw new CardNotFoundException("Card do id " + id + " não foi encontrado para realizar " +
                "uma alteração de dados do mesmo!");
    }

    public Card addPhotoInCard(Long idCard, Long idPhoto) {
        Optional<Card> cardOpt = cardRepository.findById(idCard);
        Optional<Photo> photoOpt = photoRepository.findById(idPhoto);
        if(cardOpt.isPresent() && photoOpt.isPresent()) {
            Card card = cardOpt.get();
            card.setPhoto(photoOpt.get());
            return cardRepository.save(card);
        }
        return null;
    }

    public Card removePhotoInCard(Long idCard, Long idPhoto) {
        Optional<Card> cardOpt = cardRepository.findById(idCard);
        Optional<Photo> photoOpt = photoRepository.findById(idPhoto);
        if(cardOpt.isPresent() && photoOpt.isPresent()) {
            Card card = cardOpt.get();
            Photo photo = photoOpt.get();

            if(card.getPhoto() != null && card.getPhoto().getId().equals(photo.getId())) {
                card.setPhoto(null);
                return cardRepository.save(card);
            } else {
                throw new RuntimeException("A foto não está associada a este card.");
            }
        }
        return null;
    }
}
