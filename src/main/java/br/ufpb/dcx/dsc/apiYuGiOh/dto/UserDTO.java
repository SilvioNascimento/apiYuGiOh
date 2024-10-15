package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserDTO {

    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String nome;

    @Email
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    private String username;

    @NotNull
    @NotBlank
    @NotEmpty
    private String senha;

    public UserDTO() {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
