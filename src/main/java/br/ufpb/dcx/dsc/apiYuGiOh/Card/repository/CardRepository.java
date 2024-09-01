package br.ufpb.dcx.dsc.apiYuGiOh.Card.repository;

import br.ufpb.dcx.dsc.apiYuGiOh.Card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
