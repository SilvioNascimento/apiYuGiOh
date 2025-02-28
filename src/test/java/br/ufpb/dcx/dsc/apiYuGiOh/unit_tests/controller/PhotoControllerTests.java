package br.ufpb.dcx.dsc.apiYuGiOh.unit_tests.controller;

import br.ufpb.dcx.dsc.apiYuGiOh.dto.PhotoDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Photo;
import br.ufpb.dcx.dsc.apiYuGiOh.service.PhotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhotoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhotoService photoService;

    private Photo photo;
    private PhotoDTO photoDTO;

    @BeforeEach
    void setUp() {
        photo = new Photo();
        photo.setId(1L);
        photo.setUrl("http://example.com/photo.jpg");

        photoDTO = new PhotoDTO();
        photoDTO.setId(1L);
        photoDTO.setUrl("http://example.com/photo.jpg");
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testListPhotos() throws Exception {
        List<Photo> photos = Arrays.asList(photo);
        Mockito.when(photoService.listPhotos()).thenReturn(photos);

        mockMvc.perform(get("/api/photo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(photo.getId()))
                .andExpect(jsonPath("$[0].url").value(photo.getUrl()));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testGetPhoto() throws Exception {
        Mockito.when(photoService.getPhoto(1L)).thenReturn(photo);

        mockMvc.perform(get("/api/photo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(photo.getId()))
                .andExpect(jsonPath("$.url").value(photo.getUrl()));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testCreatePhoto() throws Exception {
        Mockito.when(photoService.savePhoto(Mockito.any(Photo.class))).thenReturn(photo);

        mockMvc.perform(post("/api/photo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(photoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(photo.getId()))
                .andExpect(jsonPath("$.url").value(photo.getUrl()));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testUpdatePhoto() throws Exception {
        Mockito.when(photoService.updatePhoto(Mockito.eq(1L), Mockito.any(Photo.class))).thenReturn(photo);

        mockMvc.perform(put("/api/photo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(photoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(photo.getId()))
                .andExpect(jsonPath("$.url").value(photo.getUrl()));
    }

    @Test
    @WithMockUser(username = "xtreme_noob", roles = {"ADMIN"})
    void testDeletePhoto() throws Exception {
        Mockito.doNothing().when(photoService).deletePhoto(1L);

        mockMvc.perform(delete("/api/photo/1"))
                .andExpect(status().isOk());
    }
}
