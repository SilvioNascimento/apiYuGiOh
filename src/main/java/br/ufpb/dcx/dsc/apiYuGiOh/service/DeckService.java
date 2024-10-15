package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.exception.DeckNotFoundException;
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
        return deckRepository.findById(id).orElseThrow(() -> new DeckNotFoundException("Deck do id" + id +
                " não foi encontrado!"));
    }

    public List<Deck> listDecks() {
        return deckRepository.findAll();
    }

    public Deck createDeck(Deck d) {
        return deckRepository.save(d);
    }

    public void deleteDeck(Long id) {
        Optional<Deck> deckOpt = deckRepository.findById(id);
        if(deckOpt.isPresent()){
            deckRepository.deleteById(id);
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
        throw new DeckNotFoundException("Deck do id" + id + " não foi encontrado para realizar " +
                "uma alteração de dados do mesmo!");
    }
}
