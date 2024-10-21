package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.exception.UserNotFoundException;
import br.ufpb.dcx.dsc.apiYuGiOh.model.User;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User do id " + id +
                " não foi encontrado!"));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User createUser(User u) {
        u.setSenha(bCryptPasswordEncoder.encode(u.getSenha()));
        return userRepository.save(u);
    }

    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isPresent()) {
            userRepository.deleteById(id);
        }
        throw new UserNotFoundException("User do id " + id + " não foi encontrado para ser deletado!");
    }

    public User updateUser(Long id, User u) {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isPresent()) {
            User toUpdate = userOpt.get();
            toUpdate.setNome(u.getNome());
            toUpdate.setEmail(u.getEmail());
            toUpdate.setUsername(u.getUsername());
            toUpdate.setSenha(u.getSenha());
            toUpdate.setDecks(u.getDecks());
            return userRepository.save(toUpdate);
        }
        throw new UserNotFoundException("User do id " + id + " não foi encontrado para realizar " +
                "uma alteração de dados do mesmo!");
    }


}
