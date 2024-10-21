package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoMonster;
import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoSpell;
import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoTrap;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.CardNotFoundException;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.PhotoNotAssociatedWithCardException;
import br.ufpb.dcx.dsc.apiYuGiOh.model.*;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.CardRepository;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.PhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceTests {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private PhotoRepository photoRepository;

    @InjectMocks
    private CardService cardService;

    private CardMonster monsterCard;
    private CardSpell spellCard;
    private CardTrap trapCard;

    @BeforeEach
    void setUp() {
        // Criar um CardMonster
        monsterCard = new CardMonster();
        monsterCard.setId(1L);
        monsterCard.setNome("Blue-Eyes White Dragon");
        monsterCard.setAtk(3000);
        monsterCard.setDef(2500);
        monsterCard.setNivel(8);
        monsterCard.setTipoMonster(Arrays.asList(TipoMonster.DRAGAO, TipoMonster.NORMAL));

        // Criar um CardSpell
        spellCard = new CardSpell();
        spellCard.setId(2L);
        spellCard.setNome("Dark Hole");
        spellCard.setTipoSpell(TipoSpell.MAGIA_NORMAL);

        // Criar um CardTrap
        trapCard = new CardTrap();
        trapCard.setId(3L);
        trapCard.setNome("Mirror Force");
        trapCard.setTipoTrap(TipoTrap.TRAP_CONTRA_ATAQUE);
    }

    @Test
    void testListCards() {
        when(cardRepository.findAll()).thenReturn(Arrays.asList(monsterCard, spellCard, trapCard));
        assertEquals(3, cardService.listCards().size());
    }

    @Test
    void testGetCard() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(monsterCard));
        assertEquals(monsterCard, cardService.getCard(1L));
    }

    @Test
    void testGetCardNotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.getCard(1L));
    }

    @Test
    void testCreateCard() {
        when(cardRepository.save(any(Card.class))).thenReturn(monsterCard);
        Card createdCard = cardService.createCard(monsterCard);
        assertNotNull(createdCard);
        assertEquals("Blue-Eyes White Dragon", createdCard.getNome());
    }

    @Test
    void testDeleteCard() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(monsterCard));
        doNothing().when(cardRepository).deleteById(1L);
        cardService.deleteCard(1L);
        verify(cardRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCardThrowsExceptionWhenCardNotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.deleteCard(1L));
        verify(cardRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateCard() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(monsterCard));
        when(cardRepository.save(any(Card.class))).thenReturn(monsterCard);

        monsterCard.setNome("Blue-Eyes Ultimate Dragon");
        Card updatedCard = cardService.updateCard(1L, monsterCard);
        assertNotNull(updatedCard);
        assertEquals("Blue-Eyes Ultimate Dragon", updatedCard.getNome());
    }

    @Test
    void testUpdateCardThrowsExceptionWhenCardNotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.updateCard(1L, monsterCard));
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void testAddPhotoInCard() {
        Photo photo = new Photo();
        photo.setId(1L);
        photo.setUrl("http://example.com/photo.png");

        when(cardRepository.findById(1L)).thenReturn(Optional.of(monsterCard));
        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
        when(cardRepository.save(any(Card.class))).thenReturn(monsterCard);

        Card cardWithPhoto = cardService.addPhotoInCard(1L, 1L);
        assertNotNull(cardWithPhoto);
        assertEquals(photo, cardWithPhoto.getPhoto());
    }

    @Test
    void testRemovePhotoInCard() {
        Photo photo = new Photo();
        photo.setId(1L);
        monsterCard.setPhoto(photo);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(monsterCard));
        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
        when(cardRepository.save(any(Card.class))).thenReturn(monsterCard);

        Card cardWithoutPhoto = cardService.removePhotoInCard(1L, 1L);
        assertNotNull(cardWithoutPhoto);
        assertNull(cardWithoutPhoto.getPhoto());
    }

    @Test
    void testRemovePhotoInCardThrowsExceptionWhenPhotoNotAssociated() {
        Photo photo = new Photo();
        photo.setId(1L);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(monsterCard));
        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));

        assertThrows(PhotoNotAssociatedWithCardException.class, () -> cardService.removePhotoInCard(1L, 1L));
    }
}
