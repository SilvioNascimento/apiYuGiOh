package br.ufpb.dcx.dsc.apiYuGiOh.unit_tests.controller;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoMonster;
import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoSpell;
import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoTrap;
import br.ufpb.dcx.dsc.apiYuGiOh.dto.CardMonsterDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.dto.CardSpellDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.dto.CardTrapDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.model.*;
import br.ufpb.dcx.dsc.apiYuGiOh.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    private CardMonster cardMonster;
    private CardSpell cardSpell;
    private CardTrap cardTrap;

    private CardMonsterDTO cardMonsterDTO;
    private CardSpellDTO cardSpellDTO;
    private CardTrapDTO cardTrapDTO;

    @BeforeEach
    void setUp() {
        // Card Monster
        cardMonster = new CardMonster();
        cardMonster.setId(1L);
        cardMonster.setNome("Blue-Eyes White Dragon");
        cardMonster.setDescricao("Este dragão lendário é uma poderosa máquina de " +
                "destruição. Praticamente invencível, muito poucos enfrentaram esta " +
                "magnífica criatura e viveram para contar a história.");
        cardMonster.setAtk(3000);
        cardMonster.setDef(2500);
        cardMonster.setNivel(8);
        cardMonster.setAtributo("Light");
        cardMonster.setTipoMonster(new ArrayList<>(Arrays.asList(TipoMonster.DRAGAO, TipoMonster.NORMAL)));

        cardMonsterDTO = new CardMonsterDTO();
        cardMonsterDTO.setId(1L);
        cardMonsterDTO.setNome("Blue-Eyes White Dragon");
        cardMonsterDTO.setDescricao("Este dragão lendário é uma poderosa máquina de " +
                "destruição. Praticamente invencível, muito poucos enfrentaram esta " +
                "magnífica criatura e viveram para contar a história.");
        cardMonsterDTO.setAtk(3000);
        cardMonsterDTO.setDef(2500);
        cardMonsterDTO.setNivel(8);
        cardMonsterDTO.setAtributo("Light");
        cardMonsterDTO.setTipoMonster(new ArrayList<>(Arrays.asList(TipoMonster.DRAGAO, TipoMonster.NORMAL)));

        // Card Spell
        cardSpell = new CardSpell();
        cardSpell.setId(2L);
        cardSpell.setNome("Dark Hole");
        cardSpell.setDescricao("Destroy all monsters on the field.");
        cardSpell.setTipoSpell(TipoSpell.MAGIA_NORMAL);

        cardSpellDTO = new CardSpellDTO();
        cardSpellDTO.setId(2L);
        cardSpellDTO.setNome("Dark Hole");
        cardSpellDTO.setDescricao("Destroy all monsters on the field.");
        cardSpellDTO.setTipoSpell(TipoSpell.MAGIA_NORMAL);

        // Card Trap
        cardTrap = new CardTrap();
        cardTrap.setId(3L);
        cardTrap.setNome("Mirror Force");
        cardTrap.setDescricao("Destroy all attack position monsters your opponent controls.");
        cardTrap.setTipoTrap(TipoTrap.TRAP_CONTRA_ATAQUE);

        cardTrapDTO = new CardTrapDTO();
        cardTrapDTO.setId(3L);
        cardTrapDTO.setNome("Mirror Force");
        cardTrapDTO.setDescricao("Destroy all attack position monsters your opponent controls.");
        cardTrapDTO.setTipoTrap(TipoTrap.TRAP_CONTRA_ATAQUE);
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testListCards() throws Exception {
        when(cardService.listCards()).thenReturn(Arrays.asList(cardMonster, cardSpell, cardTrap));

        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(cardMonster.getId()))
                .andExpect(jsonPath("$[0].nome").value(cardMonster.getNome()))
                .andExpect(jsonPath("$[1].id").value(cardSpell.getId()))
                .andExpect(jsonPath("$[1].nome").value(cardSpell.getNome()))
                .andExpect(jsonPath("$[2].id").value(cardTrap.getId()))
                .andExpect(jsonPath("$[2].nome").value(cardTrap.getNome()));
    }

    @Test
    @WithMockUser(username = "maria_cecilia", roles = {"USER"})
    void testListCards2() throws Exception {
        when(cardService.listCards()).thenReturn(Arrays.asList(cardMonster, cardSpell, cardTrap));

        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(cardMonster.getId()))
                .andExpect(jsonPath("$[0].nome").value(cardMonster.getNome()))
                .andExpect(jsonPath("$[1].id").value(cardSpell.getId()))
                .andExpect(jsonPath("$[1].nome").value(cardSpell.getNome()))
                .andExpect(jsonPath("$[2].id").value(cardTrap.getId()))
                .andExpect(jsonPath("$[2].nome").value(cardTrap.getNome()));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testGetCardMonster() throws Exception {
        when(cardService.getCard(1L)).thenReturn(cardMonster);

        mockMvc.perform(get("/api/cards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cardMonster.getId()))
                .andExpect(jsonPath("$.nome").value(cardMonster.getNome()))
                .andExpect(jsonPath("$.descricao").value(cardMonster.getDescricao()))
                .andExpect(jsonPath("$.nivel").value(cardMonster.getNivel()))
                .andExpect(jsonPath("$.atk").value(cardMonster.getAtk()))
                .andExpect(jsonPath("$.def").value(cardMonster.getDef()))
                .andExpect(jsonPath("$.atributo").value(cardMonster.getAtributo()))
                .andExpect(jsonPath("$.tipoMonster[0]").value("DRAGAO")) // Primeiro elemento
                .andExpect(jsonPath("$.tipoMonster[1]").value("NORMAL"));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testGetCardSpell() throws Exception {
        when(cardService.getCard(2L)).thenReturn(cardSpell);
        System.out.println("Valor retornado: " + cardSpell.getTipoSpell());

        mockMvc.perform(get("/api/cards/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cardSpell.getId()))
                .andExpect(jsonPath("$.nome").value(cardSpell.getNome()))
                .andExpect(jsonPath("$.descricao").value(cardSpell.getDescricao()))
                .andExpect(jsonPath("$.tipoSpell").value(containsString("MAGIA_NORMAL")));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testGetCardTrap() throws Exception {
        when(cardService.getCard(3L)).thenReturn(cardTrap);

        mockMvc.perform(get("/api/cards/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cardTrap.getId()))
                .andExpect(jsonPath("$.nome").value(cardTrap.getNome()))
                .andExpect(jsonPath("$.descricao").value(cardTrap.getDescricao()))
                .andExpect(jsonPath("$.tipoTrap").value(containsString("TRAP_CONTRA_ATAQUE")));;
    }

    @Test
    @WithMockUser(username = "maria_cecilia", roles = {"USER"})
    void testGetCardMonster2() throws Exception {
        when(cardService.getCard(1L)).thenReturn(cardMonster);

        mockMvc.perform(get("/api/cards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cardMonster.getId()))
                .andExpect(jsonPath("$.nome").value(cardMonster.getNome()))
                .andExpect(jsonPath("$.descricao").value(cardMonster.getDescricao()))
                .andExpect(jsonPath("$.nivel").value(cardMonster.getNivel()))
                .andExpect(jsonPath("$.atk").value(cardMonster.getAtk()))
                .andExpect(jsonPath("$.def").value(cardMonster.getDef()))
                .andExpect(jsonPath("$.atributo").value(cardMonster.getAtributo()))
                .andExpect(jsonPath("$.tipoMonster[0]").value("DRAGAO")) // Primeiro elemento
                .andExpect(jsonPath("$.tipoMonster[1]").value("NORMAL"));
    }

    @Test
    @WithMockUser(username = "maria_cecilia", roles = {"USER"})
    void testGetCardSpell2() throws Exception {
        when(cardService.getCard(2L)).thenReturn(cardSpell);
        System.out.println("Valor retornado: " + cardSpell.getTipoSpell());

        mockMvc.perform(get("/api/cards/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cardSpell.getId()))
                .andExpect(jsonPath("$.nome").value(cardSpell.getNome()))
                .andExpect(jsonPath("$.descricao").value(cardSpell.getDescricao()))
                .andExpect(jsonPath("$.tipoSpell").value(containsString("MAGIA_NORMAL")));
    }

    @Test
    @WithMockUser(username = "maria_cecilia", roles = {"USER"})
    void testGetCardTrap2() throws Exception {
        when(cardService.getCard(3L)).thenReturn(cardTrap);

        mockMvc.perform(get("/api/cards/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cardTrap.getId()))
                .andExpect(jsonPath("$.nome").value(cardTrap.getNome()))
                .andExpect(jsonPath("$.descricao").value(cardTrap.getDescricao()))
                .andExpect(jsonPath("$.tipoTrap").value(containsString("TRAP_CONTRA_ATAQUE")));;
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testCreateCardMonster() throws Exception {
        when(cardService.createCard(Mockito.any(CardMonster.class))).thenReturn(cardMonster);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cardMonsterDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(cardMonster.getId()))
                .andExpect(jsonPath("$.nome").value(cardMonster.getNome()))
                .andExpect(jsonPath("$.descricao").value(cardMonster.getDescricao()))
                .andExpect(jsonPath("$.nivel").value(cardMonster.getNivel()))
                .andExpect(jsonPath("$.atk").value(cardMonster.getAtk()))
                .andExpect(jsonPath("$.def").value(cardMonster.getDef()))
                .andExpect(jsonPath("$.atributo").value(cardMonster.getAtributo()))
                .andExpect(jsonPath("$.tipoMonster[0]").value("DRAGAO")) // Primeiro elemento
                .andExpect(jsonPath("$.tipoMonster[1]").value("NORMAL"));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testCreateCardSpell() throws Exception {
        when(cardService.createCard(Mockito.any(CardSpell.class))).thenReturn(cardSpell);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cardSpellDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(cardSpell.getId()))
                .andExpect(jsonPath("$.nome").value(cardSpell.getNome()))
                .andExpect(jsonPath("$.descricao").value(cardSpell.getDescricao()))
                .andExpect(jsonPath("$.tipoSpell").value(containsString("MAGIA_NORMAL")));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testCreateCardTrap() throws Exception {
        when(cardService.createCard(Mockito.any(CardTrap.class))).thenReturn(cardTrap);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cardTrapDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(cardTrap.getId()))
                .andExpect(jsonPath("$.nome").value(cardTrap.getNome()))
                .andExpect(jsonPath("$.descricao").value(cardTrap.getDescricao()))
                .andExpect(jsonPath("$.tipoTrap").value(containsString("TRAP_CONTRA_ATAQUE")));;
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testDeleteCard() throws Exception {
        Mockito.doNothing().when(cardService).deleteCard(1L);

        mockMvc.perform(delete("/api/cards/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    public void testAddPhotoInCard() throws Exception {
        // Mockando um Card com uma foto adicionada
        CardMonster card = new CardMonster();
        card.setId(1L);
        card.setNome("Blue-Eyes White Dragon");
        Photo photo = new Photo("https://example.com/images/blue-eyes-white-dragon.jpg");
        card.setPhoto(photo);
        card.setDescricao("Este dragão lendário é uma poderosa máquina de " +
                "destruição. Praticamente invencível, muito poucos enfrentaram esta " +
                "magnífica criatura e viveram para contar a história.");
        card.setAtk(3000);
        card.setDef(2500);
        card.setNivel(8);
        card.setAtributo("Light");
        card.setTipoMonster(new ArrayList<>(Arrays.asList(TipoMonster.DRAGAO, TipoMonster.NORMAL)));

        // Mockando o retorno do serviço
        when(cardService.addPhotoInCard(1L, 100L)).thenReturn(card);

        // Simulando a requisição PUT para adicionar a foto
        mockMvc.perform(put("/api/cards/1/photo/100/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Blue-Eyes White Dragon"))
                .andExpect(jsonPath("$.photo.url").value("https://example.com/images/blue-eyes-white-dragon.jpg")) // Verificando a URL dentro do objeto photo
                .andExpect(jsonPath("$.descricao").value(card.getDescricao()))
                .andExpect(jsonPath("$.nivel").value(card.getNivel()))
                .andExpect(jsonPath("$.atk").value(card.getAtk()))
                .andExpect(jsonPath("$.def").value(card.getDef()))
                .andExpect(jsonPath("$.atributo").value(card.getAtributo()))
                .andExpect(jsonPath("$.tipoMonster[0]").value("DRAGAO")) // Primeiro elemento
                .andExpect(jsonPath("$.tipoMonster[1]").value("NORMAL")); // Segundo elemento
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    public void testRemovePhotoInCard() throws Exception {
        // Mockando um Card sem uma foto (após remoção)
        CardMonster card = new CardMonster();
        card.setId(1L);
        card.setNome("Blue-Eyes White Dragon");
        card.setPhoto(null); // Foto removida
        card.setDescricao("Este dragão lendário é uma poderosa máquina de " +
                "destruição. Praticamente invencível, muito poucos enfrentaram esta " +
                "magnífica criatura e viveram para contar a história.");
        card.setAtk(3000);
        card.setDef(2500);
        card.setNivel(8);
        card.setAtributo("Light");
        card.setTipoMonster(new ArrayList<>(Arrays.asList(TipoMonster.DRAGAO, TipoMonster.NORMAL)));

        // Mockando o retorno do serviço após a remoção da foto
        when(cardService.removePhotoInCard(1L, 100L)).thenReturn(card);

        // Simulando a requisição PUT para remover a foto
        mockMvc.perform(put("/api/cards/1/photo/100/remove"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Blue-Eyes White Dragon"))
                .andExpect(jsonPath("$.photo").isEmpty()) // Verifica que o campo photo está vazio ou nulo
                .andExpect(jsonPath("$.descricao").value(card.getDescricao()))
                .andExpect(jsonPath("$.nivel").value(card.getNivel()))
                .andExpect(jsonPath("$.atk").value(card.getAtk()))
                .andExpect(jsonPath("$.def").value(card.getDef()))
                .andExpect(jsonPath("$.atributo").value(card.getAtributo()))
                .andExpect(jsonPath("$.tipoMonster[0]").value("DRAGAO")) // Primeiro elemento
                .andExpect(jsonPath("$.tipoMonster[1]").value("NORMAL")); // Segundo elemento
    }

}
