package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.Role;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.UserAlreadyExistsException;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.UserNotFoundException;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Deck;
import br.ufpb.dcx.dsc.apiYuGiOh.model.User;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.DeckRepository;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private User userWithDeck;
    private Deck deck;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setNome("Maria");
        user.setEmail("maria@gmail.com");
        user.setUsername("maria_cecilia");
        user.setSenha("m123");
        user.setRole(Role.USER);

        userWithDeck = new User();
        user.setId(2L);
        user.setNome("Silvio");
        user.setEmail("silvio@gmail.com");
        user.setUsername("silvio");
        user.setSenha("s123");
        user.setRole(Role.ADMIN);

        deck = new Deck();
        deck.setId(1L);
        deck.setNomeDeck("Deck do Mago Negro");
        deck.setUser(userWithDeck);

        userWithDeck.addDeck(deck);
    }

    @Test
    void testListUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        assertEquals(1, userService.listUsers().size());
    }

    @Test
    void testGetUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertEquals(user, userService.getUser(1L));
    }

    @Test
    void testGetUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUser(1L));
    }

    @Test
    void testCreateUser() {
        when(bCryptPasswordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        //when(deckRepository.save(any(Deck.class))).thenReturn(new Deck("Deck dos Dragões dos olhos azuis"));

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("encodedPassword", createdUser.getSenha());
    }

    @Test
    void testCreateUserThrowsExceptionWhenUsernameAlreadyExists() {
        User existingUser = new User();
        existingUser.setUsername("existingUsername");
        when(userRepository.findByUsername("existingUsername")).thenReturn(existingUser);

        User newUser = new User();
        newUser.setUsername("existingUsername");

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(newUser);
        });
        verify(userRepository, times(1)).findByUsername("existingUsername");
    }

    @Test
    void testDeleteUserThrowsExceptionWhenUserNotFound() {
        // Simulando que o usuário com ID 1 não foi encontrado
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica se o método lança a exceção correta
        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });

        // Verifica se o método findById foi chamado uma vez com o ID correto
        verify(userRepository, times(1)).findById(1L);

        // Verifica se o método deleteById nunca foi chamado, pois o usuário não foi encontrado
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        user.setNome("Maria");

        User updatedUser = userService.updateUser(1L, user);
        assertNotNull(updatedUser);
        assertEquals("Maria", updatedUser.getNome()); // Verificando se o nome permanece "Maria"
    }

    @Test
    void testUpdateUserThrowsExceptionWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User userToUpdate = new User();
        userToUpdate.setNome("Novo Nome");
        userToUpdate.setEmail("novoemail@gmail.com");

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(1L, userToUpdate);
        });

        // Verifica se o método findById foi chamado uma vez com o ID correto
        verify(userRepository, times(1)).findById(1L);

        // Verifica se o método save nunca foi chamado, pois o usuário não foi encontrado
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAddDeck() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userWithDeck = userService.addDeckInUser(1L, 1L);
        assertNotNull(userWithDeck);
        assertTrue(userWithDeck.getDecks().contains(deck));
    }

    @Test
    void testRemoveDeck() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        user.addDeck(deck);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User userWithoutDeck = userService.removeDeckInUser(1L, 1L);
        assertNotNull(userWithoutDeck);
        assertFalse(userWithoutDeck.getDecks().contains(deck));
        verify(userRepository, times(1)).save(user);
    }
}
