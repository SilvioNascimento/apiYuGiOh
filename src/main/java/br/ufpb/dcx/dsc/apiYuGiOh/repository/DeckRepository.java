package br.ufpb.dcx.dsc.apiYuGiOh.repository;

import br.ufpb.dcx.dsc.apiYuGiOh.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Long> {

}
