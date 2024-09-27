package br.ufpb.dcx.dsc.apiYuGiOh.service;

import br.ufpb.dcx.dsc.apiYuGiOh.model.Photo;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    private PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo getPhoto(Long id) {
        return photoRepository.getReferenceById(id);
    }

    public List<Photo> listPhotos() {
        return photoRepository.findAll();
    }

    public Photo savePhoto(Photo p) {
        return photoRepository.save(p);
    }

    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }

    public Photo updatePhoto(Long id, Photo p) {
        Optional<Photo> photoOpt = photoRepository.findById(id);
        if(photoOpt.isPresent()) {
            Photo toUpdate = photoOpt.get();
            toUpdate.setUrl(p.getUrl());
            return photoRepository.save(toUpdate);
        }
        return null;
    }
}
