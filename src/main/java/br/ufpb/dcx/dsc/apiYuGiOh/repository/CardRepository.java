package br.ufpb.dcx.dsc.apiYuGiOh.repository;

import br.ufpb.dcx.dsc.apiYuGiOh.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
