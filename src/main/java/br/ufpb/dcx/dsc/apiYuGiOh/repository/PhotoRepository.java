package br.ufpb.dcx.dsc.apiYuGiOh.repository;

import br.ufpb.dcx.dsc.apiYuGiOh.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Photo findByUrl(String url);
}
