package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.exception.CardNotAssociatedWithDeckException;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.DeckAlreadyExistsException;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.DeckNotFoundException;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Card;
import br.ufpb.dcx.dsc.apiYuGiOh.model.CardMonster;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Deck;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.CardRepository;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.DeckRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DeckService {
    private DeckRepository deckRepository;
    private CardRepository cardRepository;

    public DeckService(DeckRepository deckRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
    }

    public Deck getDeck(Long id) {
        return deckRepository.findById(id).orElseThrow(() -> new DeckNotFoundException("Deck do id " + id +
                " não foi encontrado!"));
    }

    public List<Deck> listDecks() {
        return deckRepository.findAll();
    }

    public Deck createDeck(Deck d) {
        if(deckRepository.findByNomeDeck(d.getNomeDeck()) != null) {
            throw new DeckAlreadyExistsException("Deck com o nome " + d.getNomeDeck() +
                    " já existe.");
        }
        return deckRepository.save(d);
    }

    public void deleteDeck(Long id) {
        Optional<Deck> deckOpt = deckRepository.findById(id);
        if(deckOpt.isPresent()){
            deckRepository.deleteById(id);
            return;
        }

        throw new DeckNotFoundException("Deck do id " + id + " não foi encontrado para ser deletado!");
    }

    public Deck updateDeck(Long id, Deck d) {
        Optional<Deck> deckOpt = deckRepository.findById(id);
        if(deckOpt.isPresent()) {
            Deck toUpdate = deckOpt.get();
            toUpdate.setNomeDeck(d.getNomeDeck());
            toUpdate.setUser(d.getUser());
            return deckRepository.save(toUpdate);
        }
        throw new DeckNotFoundException("Deck do id " + id + " não foi encontrado para realizar " +
                "uma alteração de dados do mesmo!");
    }

    // Colocar uma exceção que informa que não pode adicionar a mesma Card mais de 3 vezes
    // Colocar uma exceção que informa que não pode adicionar mais de 60 cards
    public Deck addCardInDeck(Long idDeck, Long idCard) {
        Optional<Deck> deckOpt = deckRepository.findById(idDeck);
        Optional<Card> cardOpt = cardRepository.findById(idCard);
        if(deckOpt.isPresent() && cardOpt.isPresent()) {
            Deck d = deckOpt.get();
            Card c = cardOpt.get();
            d.getCards().add(c);
            return deckRepository.save(d);
        }
        return null;
    }

    public Deck removeCardInDeck(Long idDeck, Long idCard) {
        Optional<Deck> deckOpt = deckRepository.findById(idDeck);
        Optional<Card> cardOpt = cardRepository.findById(idCard);
        if(deckOpt.isPresent() && cardOpt.isPresent()) {
            Deck d = deckOpt.get();
            Card c = cardOpt.get();

            if(d.getCards().contains(c)) {
                d.getCards().remove(c);
                return deckRepository.save(d);
            } else {
                throw new CardNotAssociatedWithDeckException("O Card não está associado a este Deck.");
            }
        }
        return null;
    }
}
