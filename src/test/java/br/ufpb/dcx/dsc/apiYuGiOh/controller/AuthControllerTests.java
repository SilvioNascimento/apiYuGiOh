package br.ufpb.dcx.dsc.apiYuGiOh.controller;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.Role;
import br.ufpb.dcx.dsc.apiYuGiOh.dto.ChangePasswordRequestDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.dto.LoginRequestDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.dto.UserDTOResponse;
import br.ufpb.dcx.dsc.apiYuGiOh.model.User;
import br.ufpb.dcx.dsc.apiYuGiOh.security.CustomUserDetailsService;
import br.ufpb.dcx.dsc.apiYuGiOh.security.JwtUtil;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    private UserDTOResponse userDTOResponse;

    private LoginRequestDTO loginRequestDTO;

    private ChangePasswordRequestDTO changePasswordRequestDTO;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        userDTOResponse = new UserDTOResponse();
        userDTOResponse.setNome("Silvio");
        userDTOResponse.setEmail("silvio@gmail.com");
        userDTOResponse.setUsername("Xtreme_noob");

        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("Noob");
        loginRequestDTO.setSenha("noob123");

        changePasswordRequestDTO = new ChangePasswordRequestDTO();
        changePasswordRequestDTO.setUsername("Xtreme_noob");
        changePasswordRequestDTO.setSenha("xtreme_noob123");
    }

    @Test
    void testUsernameNotFoundException() throws Exception {
        Mockito.when(customUserDetailsService.loadUserByUsername(loginRequestDTO.getUsername()))
                .thenThrow(new UsernameNotFoundException("User "+ loginRequestDTO.getUsername()
                        + " não foi encontrado!"));

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").value("User " + loginRequestDTO.getUsername()
                        + " não foi encontrado!"));
    }

    @Test
    void testRegisterUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setNome("Silvio");
        user.setEmail("silvio@gmail.com");
        user.setUsername("Xtreme_noob");
        user.setSenha("xtreme123");
        user.setRole(Role.USER);

        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(userDTOResponse.getNome()))
                .andExpect(jsonPath("$.email").value(userDTOResponse.getEmail()))
                .andExpect(jsonPath("$.username").value(userDTOResponse.getUsername()));
    }

    @Test
    void testCreateAuthenticationToken() throws Exception {
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("Noob", "noob123"));

        Mockito.when(customUserDetailsService.loadUserByUsername("Noob")).thenReturn(userDetails);

        Mockito.when(jwtUtil.generateToken(userDetails)).thenReturn("mock-jwt-token");

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }

    @Test
    void testChangePassword() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("Xtreme_noob");
        user.setSenha("xtreme_noob123");

        Mockito.when(userService.getUserByUsername(changePasswordRequestDTO.getUsername())).thenReturn(user);

        mockMvc.perform(post("/api/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(changePasswordRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Senha alterada com sucesso!"));
    }

    @Test
    void testUserNotFoundException() throws Exception {
        ChangePasswordRequestDTO changePasswordRequestDTO2 = new ChangePasswordRequestDTO();
        changePasswordRequestDTO2.setUsername("NonExistentUser");
        changePasswordRequestDTO2.setSenha("new_password123");

        Mockito.when(userService.getUserByUsername(changePasswordRequestDTO2.getUsername())).thenReturn(null);

        mockMvc.perform(post("/api/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(changePasswordRequestDTO2)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").value("User " +
                        changePasswordRequestDTO2.getUsername() + " não foi encontrado!"));
    }
}
