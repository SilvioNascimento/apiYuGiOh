package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.Role;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.UserAlreadyExistsException;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.UserNotFoundException;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Deck;
import br.ufpb.dcx.dsc.apiYuGiOh.model.User;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.DeckRepository;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    private DeckRepository deckRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, DeckRepository deckRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.deckRepository = deckRepository;
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
        if(userRepository.findByUsername(u.getUsername()) != null) {
            throw new UserAlreadyExistsException("User com username " + u.getUsername() +
                    " já existe.");
        }
        u.setSenha(bCryptPasswordEncoder.encode(u.getSenha()));
        u.setRole(Role.USER);
        return userRepository.save(u);
    }

    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isPresent()) {
            userRepository.deleteById(id);
            return;
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
            if (!u.getSenha().equals(toUpdate.getSenha())) {
                toUpdate.setSenha(bCryptPasswordEncoder.encode(u.getSenha()));
            }
            toUpdate.setDecks(u.getDecks());
            return userRepository.save(toUpdate);
        }
        throw new UserNotFoundException("User do id " + id + " não foi encontrado para realizar " +
                "uma alteração de dados do mesmo!");
    }

    // Criar uma exceção personalizada, onde informa que ou user
    // ou deck não existe
    public User addDeckInUser(Long idUser, Long idDeck) {
        Optional<User> userOpt = userRepository.findById(idUser);
        Optional<Deck> deckOpt = deckRepository.findById(idDeck);
        if (userOpt.isPresent() && deckOpt.isPresent()) {
            User user = userOpt.get();
            Deck deck = deckOpt.get();

            user.addDeck(deck);
            return userRepository.save(user);
        }
        return null;
    }

    // Criar uma exceção personalizada, onde informa que ou user
    // ou deck não existe
    public User removeDeckInUser(Long idUser, Long idDeck) {
        Optional<User> userOpt = userRepository.findById(idUser);
        Optional<Deck> deckOpt = deckRepository.findById(idDeck);
        if (userOpt.isPresent() && deckOpt.isPresent()) {
            User user = userOpt.get();
            Deck deck = deckOpt.get();

            user.removeDeck(deck);
            return userRepository.save(user);
        }
        return null;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("Usuário " + username + " não foi encontrado!");
        }
        return user;
    }

}
