package br.ufpb.dcx.dsc.apiYuGiOh.repository;

import br.ufpb.dcx.dsc.apiYuGiOh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
