package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.Role;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Deck;
import br.ufpb.dcx.dsc.apiYuGiOh.validation.EmailExistente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UserDTOResponse {

    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String nome;

    @Email
    @EmailExistente
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    private String username;

    private List<Deck> decks;

    private Role role;

    public UserDTOResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTOResponse that = (UserDTOResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username);
    }

    @Override
    public String toString() {
        return "UserDTOResponse{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", decks=" + decks +
                ", role=" + role +
                '}';
    }
}
