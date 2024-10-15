package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.model.Deck;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.DeckRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeckService {
    private DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public Deck getDeck(Long id) {
        return deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck não encontrado"));
    }

    public List<Deck> listDecks() {
        return deckRepository.findAll();
    }

    public Deck createDeck(Deck d) {
        return deckRepository.save(d);
    }

    public void deleteUser(Long id) {
        deckRepository.deleteById(id);
    }

    public Deck updateDeck(Long id, Deck d) {
        Optional<Deck> deckOpt = deckRepository.findById(id);
        if(deckOpt.isPresent()) {
            Deck toUpdate = deckOpt.get();
            toUpdate.setNomeDeck(d.getNomeDeck());
            toUpdate.setUser(d.getUser());
            return deckRepository.save(toUpdate);
        }
        return null;
    }
}
