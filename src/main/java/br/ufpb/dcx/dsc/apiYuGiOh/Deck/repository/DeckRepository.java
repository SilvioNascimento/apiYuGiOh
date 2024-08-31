package br.ufpb.dcx.dsc.apiYuGiOh.Deck.repository;

import br.ufpb.dcx.dsc.apiYuGiOh.Deck.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Long> {

}
