package br.ufpb.dcx.dsc.apiYuGiOh.controller;

import br.ufpb.dcx.dsc.apiYuGiOh.dto.DeckDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Deck;
import br.ufpb.dcx.dsc.apiYuGiOh.service.DeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeckControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeckService deckService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    private Deck deck;
    private DeckDTO deckDTO;

    @BeforeEach
    public void setup() {
        // Mock Deck entity
        deck = new Deck();
        deck.setId(1L);
        deck.setNomeDeck("Dragon Deck");

        // Mock DeckDTO
        deckDTO = new DeckDTO();
        deckDTO.setId(1L);
        deckDTO.setNomeDeck("Dragon Deck");
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    public void testCreateDeck() throws Exception {
        // Mockando o comportamento do serviço
        when(deckService.createDeck(Mockito.any(Deck.class))).thenReturn(deck);

        // Simulando a requisição POST para criar o deck
        mockMvc.perform(post("/api/deck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deckDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nomeDeck").value("Dragon Deck"));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    public void testGetDeck() throws Exception {
        // Mockando o comportamento do serviço
        when(deckService.getDeck(1L)).thenReturn(deck);

        // Simulando a requisição GET para obter o deck
        mockMvc.perform(get("/api/deck/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nomeDeck").value("Dragon Deck"));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    public void testUpdateDeck() throws Exception {
        when(deckService.updateDeck(Mockito.eq(1L), Mockito.any(Deck.class))).thenReturn(deck);

        // Simulando a requisição PUT para atualizar o deck
        mockMvc.perform(put("/api/deck/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deckDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nomeDeck").value("Dragon Deck"));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    public void testAddCardInDeck() throws Exception {
        // Mockando o comportamento do serviço
        when(deckService.addCardInDeck(1L, 100L)).thenReturn(deck);

        // Simulando a requisição PUT para adicionar uma carta ao deck
        mockMvc.perform(put("/api/deck/1/card/100/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nomeDeck").value("Dragon Deck"));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    public void testRemoveCardInDeck() throws Exception {
        // Mockando o comportamento do serviço
        when(deckService.removeCardInDeck(1L, 100L)).thenReturn(deck);

        // Simulando a requisição PUT para remover uma carta do deck
        mockMvc.perform(put("/api/deck/1/card/100/remove"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nomeDeck").value("Dragon Deck"));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    public void testDeleteDeck() throws Exception {
        // Simulando a requisição DELETE para excluir o deck
        mockMvc.perform(delete("/api/deck/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    public void testListDecks() throws Exception {
        // Mockando o comportamento do serviço
        when(deckService.listDecks()).thenReturn(Collections.singletonList(deck));

        // Simulando a requisição GET para listar os decks
        mockMvc.perform(get("/api/deck"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nomeDeck").value("Dragon Deck"));
    }
}
