package br.ufpb.dcx.dsc.apiYuGiOh.dto;

public class LoginRequestDTO {
    private String username;
    private String senha;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
