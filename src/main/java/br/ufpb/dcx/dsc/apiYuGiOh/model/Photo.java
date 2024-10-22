package br.ufpb.dcx.dsc.apiYuGiOh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @Column(name = "url")
    private String url;

    @OneToOne(mappedBy = "photo")
    private Card card;

    public Photo(){
    }

    public Photo(String url) {
        this.url = url;
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
