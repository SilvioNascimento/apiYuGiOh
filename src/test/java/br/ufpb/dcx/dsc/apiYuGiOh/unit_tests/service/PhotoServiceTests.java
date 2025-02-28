package br.ufpb.dcx.dsc.apiYuGiOh.unit_tests.service;

import br.ufpb.dcx.dsc.apiYuGiOh.exception.PhotoAlreadyExistsException;
import br.ufpb.dcx.dsc.apiYuGiOh.exception.PhotoNotFoundException;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Photo;
import br.ufpb.dcx.dsc.apiYuGiOh.repository.PhotoRepository;
import br.ufpb.dcx.dsc.apiYuGiOh.service.PhotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceTests {

    @Mock
    private PhotoRepository photoRepository;

    @InjectMocks
    private PhotoService photoService;

    private Photo photo;

    @BeforeEach
    void setUp() {
        photo = new Photo();
        photo.setId(1L);
        photo.setUrl("http://example.com/photo1.jpg");
    }

    @Test
    void testListPhotos() {
        when(photoRepository.findAll()).thenReturn(Arrays.asList(photo));
        assertEquals(1, photoService.listPhotos().size());
    }

    @Test
    void testGetPhoto() {
        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
        assertEquals(photo, photoService.getPhoto(1L));
    }

    @Test
    void testGetPhotoNotFound() {
        when(photoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PhotoNotFoundException.class, () -> photoService.getPhoto(1L));
    }

    @Test
    void testCreatePhoto() {
        when(photoRepository.findByUrl("http://example.com/photo1.jpg")).thenReturn(null);
        when(photoRepository.save(any(Photo.class))).thenReturn(photo);

        Photo createdPhoto = photoService.savePhoto(photo);
        assertNotNull(createdPhoto);
        assertEquals("http://example.com/photo1.jpg", createdPhoto.getUrl());
    }

    @Test
    void testCreatePhotoThrowsExceptionWhenUrlAlreadyExists() {
        Photo existingPhoto = new Photo();
        existingPhoto.setUrl("http://example.com/photo1.jpg");

        when(photoRepository.findByUrl("http://example.com/photo1.jpg")).thenReturn(existingPhoto);

        Photo newPhoto = new Photo();
        newPhoto.setUrl("http://example.com/photo1.jpg");

        assertThrows(PhotoAlreadyExistsException.class, () -> {
            photoService.savePhoto(newPhoto);
        });
        verify(photoRepository, times(1)).findByUrl("http://example.com/photo1.jpg");
    }

    @Test
    void testDeletePhoto() {
        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
        doNothing().when(photoRepository).deleteById(1L);

        photoService.deletePhoto(1L);
        verify(photoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePhotoThrowsExceptionWhenPhotoNotFound() {
        when(photoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PhotoNotFoundException.class, () -> {
            photoService.deletePhoto(1L);
        });

        verify(photoRepository, times(1)).findById(1L);
        verify(photoRepository, never()).deleteById(1L);
    }

    @Test
    void testUpdatePhoto() {
        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
        when(photoRepository.save(any(Photo.class))).thenReturn(photo);

        photo.setUrl("http://example.com/photo2.jpg");

        Photo updatedPhoto = photoService.updatePhoto(1L, photo);
        assertNotNull(updatedPhoto);
        assertEquals("http://example.com/photo2.jpg", updatedPhoto.getUrl());
    }

    @Test
    void testUpdatePhotoThrowsExceptionWhenPhotoNotFound() {
        when(photoRepository.findById(1L)).thenReturn(Optional.empty());

        Photo photoToUpdate = new Photo();
        photoToUpdate.setUrl("http://example.com/photo2.jpg");

        assertThrows(PhotoNotFoundException.class, () -> {
            photoService.updatePhoto(1L, photoToUpdate);
        });

        verify(photoRepository, times(1)).findById(1L);
        verify(photoRepository, never()).save(any(Photo.class));
    }
}
