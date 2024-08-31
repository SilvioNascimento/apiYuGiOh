package br.ufpb.dcx.dsc.apiYuGiOh.User.repository;

import br.ufpb.dcx.dsc.apiYuGiOh.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
