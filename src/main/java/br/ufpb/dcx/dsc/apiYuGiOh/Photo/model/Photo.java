package br.ufpb.dcx.dsc.apiYuGiOh.Photo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "photo_id")
    private Long id;

    @Column(name = "url")
    private String url;

    public Photo(){
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
