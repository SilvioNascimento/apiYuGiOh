package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoSpell;
import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoTrap;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.CardNotAssociatedWithDeckException;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.DeckAlreadyExistsException;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.DeckNotFoundException;
import br.ufpb.dcx.dsc.apiYuGiOh.model.*;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.CardRepository;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.DeckRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeckServiceTests {

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private DeckService deckService;

    private Deck deck;
    private CardMonster monsterCard;
    private CardSpell spellCard;
    private CardTrap trapCard;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        deck.setId(1L);
        deck.setNomeDeck("Deck do Dragão Supremo");

        // Criando um Monster Card
        monsterCard = new CardMonster();
        monsterCard.setId(1L);
        monsterCard.setNome("Dragão Branco de Olhos Azuis");
        ((CardMonster) monsterCard).setAtk(3000);
        ((CardMonster) monsterCard).setDef(2500);
        ((CardMonster) monsterCard).setNivel(8);

        // Criando um Spell Card
        spellCard = new CardSpell();
        spellCard.setId(2L);
        spellCard.setNome("Fusão do Dragão");
        ((CardSpell) spellCard).setTipoSpell(TipoSpell.MAGIA_RITUAL);

        // Criando um Trap Card
        trapCard = new CardTrap();
        trapCard.setId(3L);
        trapCard.setNome("Armadilha do Espelho");
        ((CardTrap) trapCard).setTipoTrap(TipoTrap.TRAP_CONTRA_ATAQUE);
    }

    @Test
    void testListDecks() {
        when(deckRepository.findAll()).thenReturn(Arrays.asList(deck));
        assertEquals(1, deckService.listDecks().size());
    }

    @Test
    void testGetDeck() {
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        assertEquals(deck, deckService.getDeck(1L));
    }

    @Test
    void testGetDeckNotFound() {
        when(deckRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DeckNotFoundException.class, () -> deckService.getDeck(1L));
    }

    @Test
    void testCreateDeck() {
        when(deckRepository.save(any(Deck.class))).thenReturn(deck);
        Deck createdDeck = deckService.createDeck(deck);
        assertNotNull(createdDeck);
        assertEquals("Deck do Dragão Supremo", createdDeck.getNomeDeck());
    }

    @Test
    void testCreateDeckThrowsExceptionWhenDeckAlreadyExists() {
        when(deckRepository.findByNomeDeck("Deck do Dragão Supremo")).thenReturn(deck);
        assertThrows(DeckAlreadyExistsException.class, () -> deckService.createDeck(deck));
    }

    @Test
    void testDeleteDeck() {
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        doNothing().when(deckRepository).deleteById(1L);
        deckService.deleteDeck(1L);
        verify(deckRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDeckThrowsExceptionWhenDeckNotFound() {
        when(deckRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DeckNotFoundException.class, () -> deckService.deleteDeck(1L));
        verify(deckRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateDeck() {
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        when(deckRepository.save(any(Deck.class))).thenReturn(deck);

        deck.setNomeDeck("Novo Nome do Deck");
        Deck updatedDeck = deckService.updateDeck(1L, deck);
        assertNotNull(updatedDeck);
        assertEquals("Novo Nome do Deck", updatedDeck.getNomeDeck());
    }

    @Test
    void testUpdateDeckThrowsExceptionWhenDeckNotFound() {
        when(deckRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DeckNotFoundException.class, () -> deckService.updateDeck(1L, deck));
        verify(deckRepository, never()).save(any(Deck.class));
    }

    @Test
    void testAddMonsterCardInDeck() {
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(1L)).thenReturn(Optional.of(monsterCard));
        when(deckRepository.save(any(Deck.class))).thenReturn(deck);

        Deck deckWithCard = deckService.addCardInDeck(1L, 1L);
        assertNotNull(deckWithCard);
        assertTrue(deckWithCard.getCards().contains(monsterCard));
    }

    @Test
    void testAddSpellCardInDeck() {
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(spellCard));
        when(deckRepository.save(any(Deck.class))).thenReturn(deck);

        Deck deckWithCard = deckService.addCardInDeck(1L, 2L);
        assertNotNull(deckWithCard);
        assertTrue(deckWithCard.getCards().contains(spellCard));
    }

    @Test
    void testAddTrapCardInDeck() {
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(3L)).thenReturn(Optional.of(trapCard));
        when(deckRepository.save(any(Deck.class))).thenReturn(deck);

        Deck deckWithCard = deckService.addCardInDeck(1L, 3L);
        assertNotNull(deckWithCard);
        assertTrue(deckWithCard.getCards().contains(trapCard));
    }

    @Test
    void testRemoveCardInDeck() {
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(1L)).thenReturn(Optional.of(monsterCard));
        deck.getCards().add(monsterCard);

        when(deckRepository.save(any(Deck.class))).thenReturn(deck);

        Deck deckWithoutCard = deckService.removeCardInDeck(1L, 1L);
        assertNotNull(deckWithoutCard);
        assertFalse(deckWithoutCard.getCards().contains(monsterCard));
        verify(deckRepository, times(1)).save(deck);
    }

    @Test
    void testRemoveCardInDeckThrowsExceptionWhenCardNotAssociated() {
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(1L)).thenReturn(Optional.of(monsterCard));

        assertThrows(CardNotAssociatedWithDeckException.class, () -> deckService.removeCardInDeck(1L, 1L));
    }
}
