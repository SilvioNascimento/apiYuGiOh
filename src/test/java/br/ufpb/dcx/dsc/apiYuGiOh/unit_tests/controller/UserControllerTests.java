package br.ufpb.dcx.dsc.apiYuGiOh.unit_tests.controller;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.Role;
import br.ufpb.dcx.dsc.apiYuGiOh.model.User;
import br.ufpb.dcx.dsc.apiYuGiOh.service.UserService;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();  // Agora estamos inicializando o campo de classe `user1`
        user1.setId(1L);
        user1.setNome("Silvio");
        user1.setEmail("silvio@gmail.com");
        user1.setUsername("xtreme_noob");
        user1.setSenha("s123");
        user1.setRole(Role.ADMIN);

        user2 = new User();
        user1.setId(2L);
        user1.setNome("Maria");
        user1.setEmail("maria@gmail.com");
        user1.setUsername("maria_cecilia");
        user1.setSenha("m123");
        user1.setRole(Role.USER);
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testListUsers() throws Exception {
        Mockito.when(userService.listUsers()).thenReturn(Arrays.asList(user1));

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(user1.getId()))
                .andExpect(jsonPath("$[0].nome").value(user1.getNome()));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testGetUser() throws Exception {
        Mockito.when(userService.getUser(1L)).thenReturn(user1);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.nome").value(user1.getNome()));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testCreateUser() throws Exception {
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user1);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.nome").value(user1.getNome()));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testUpdateUser() throws Exception {
        Mockito.when(userService.updateUser(Mockito.eq(1L), Mockito.any(User.class))).thenReturn(user1);

        mockMvc.perform(put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.nome").value(user1.getNome()));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testAddDeckInUser() throws Exception {
        Mockito.when(userService.addDeckInUser(1L, 1L)).thenReturn(user1);

        mockMvc.perform(put("/api/user/1/deck/1/add"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.nome").value(user1.getNome()));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testRemoveDeckInUser() throws Exception {
        Mockito.when(userService.removeDeckInUser(1L, 1L)).thenReturn(user1);

        mockMvc.perform(put("/api/user/1/deck/1/remove"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.nome").value(user1.getNome()));
    }

    @Test
    @WithMockUser(username = "maria_cecilia", roles = {"USER"})
    void testAddDeckInUser2() throws Exception {
        Mockito.when(userService.addDeckInUser(2L, 2L)).thenReturn(user2);

        mockMvc.perform(put("/api/user/2/deck/2/add"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user2.getId()))
                .andExpect(jsonPath("$.nome").value(user2.getNome()));
    }

    @Test
    @WithMockUser(username = "maria_cecilia", roles = {"USER"})
    void testRemoveDeckInUser2() throws Exception {
        Mockito.when(userService.removeDeckInUser(2L, 2L)).thenReturn(user2);

        mockMvc.perform(put("/api/user/2/deck/2/remove"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user2.getId()))
                .andExpect(jsonPath("$.nome").value(user2.getNome()));
    }
}
