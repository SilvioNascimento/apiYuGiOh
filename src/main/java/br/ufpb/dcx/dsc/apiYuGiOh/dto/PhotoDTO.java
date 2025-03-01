package br.ufpb.dcx.dsc.apiYuGiOh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PhotoDTO {

    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String url;

    public PhotoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
