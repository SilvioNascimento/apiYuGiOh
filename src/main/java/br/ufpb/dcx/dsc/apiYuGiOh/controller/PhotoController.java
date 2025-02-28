package br.ufpb.dcx.dsc.apiYuGiOh.controller;

import br.ufpb.dcx.dsc.apiYuGiOh.dto.PhotoDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Photo;
import br.ufpb.dcx.dsc.apiYuGiOh.service.PhotoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@Validated
public class PhotoController {
    private ModelMapper modelMapper;
    private final PhotoService photoService;

    public PhotoController(ModelMapper modelMapper, PhotoService photoService) {
        this.modelMapper = modelMapper;
        this.photoService = photoService;
    }

    @GetMapping("/photo/{photoId}")
    public PhotoDTO getPhoto(@PathVariable Long photoId) {
        Photo p = photoService.getPhoto(photoId);
        return convertToDTO(p);
    }

    @GetMapping("/photo")
    public List<PhotoDTO> listPhotos() {
        List<Photo> photos = photoService.listPhotos();
        return photos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoDTO createPhoto(@Valid @RequestBody PhotoDTO photoDTO) {
        Photo p = convertToEntity(photoDTO);
        Photo photoCreated = photoService.savePhoto(p);
        return convertToDTO(photoCreated);
    }

    @PutMapping("/photo/{photoId}")
    public PhotoDTO updatePhoto(@PathVariable Long photoId,
                                @Valid @RequestBody PhotoDTO photoDTO) {
        Photo p = convertToEntity(photoDTO);
        Photo photoCreated = photoService.updatePhoto(photoId, p);
        return convertToDTO(photoCreated);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/photo/{photoId}")
    public void deletePhoto(@PathVariable Long photoId) {
        photoService.deletePhoto(photoId);
    }

    private PhotoDTO convertToDTO(Photo p) {
        return modelMapper.map(p, PhotoDTO.class);
    }

    private Photo convertToEntity(PhotoDTO photoDTO) {
        return modelMapper.map(photoDTO, Photo.class);
    }
}
