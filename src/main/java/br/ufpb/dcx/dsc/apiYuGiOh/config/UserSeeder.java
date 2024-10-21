package br.ufpb.dcx.dsc.apiYuGiOh.config;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.Role;
import br.ufpb.dcx.dsc.apiYuGiOh.model.User;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserSeeder {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void seedUser() {
        if(userRepository.findByUsername("user") == null) {
            User user = new User();
            user.setNome("User");
            user.setEmail("user@gmail.com");
            user.setUsername("user");
            user.setSenha(passwordEncoder.encode("user123"));
            user.setRoles(Set.of(Role.USER));
            userRepository.save(user);
        }
    }
}
