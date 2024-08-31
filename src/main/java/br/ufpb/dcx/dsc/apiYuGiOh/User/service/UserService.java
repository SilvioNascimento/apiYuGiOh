package br.ufpb.dcx.dsc.apiYuGiOh.User.service;

import br.ufpb.dcx.dsc.apiYuGiOh.User.model.User;
import br.ufpb.dcx.dsc.apiYuGiOh.User.repository.UserRepository;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.getReferenceById(id);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User createUser(User u) {
        return userRepository.save(u);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User u) {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isPresent()) {
            User toUpdate = userOpt.get();
            toUpdate.setNome(u.getNome());
            toUpdate.setEmail(u.getEmail());
            toUpdate.setSenha(u.getSenha());
            toUpdate.setDecks(u.getDecks());
            return userRepository.save(toUpdate);
        }
        return null;
    }


}
