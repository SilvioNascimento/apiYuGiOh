package br.ufpb.dcx.dsc.apiYuGiOh.tests.integration;

import br.ufpb.dcx.dsc.apiYuGiOh.service.PhotoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "testuser", roles = {"ADMIN"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PhotoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhotoService photoService;

    @BeforeEach
    void setup() {}

    @Test
    @Order(1)
    public void testCreatePhoto() throws Exception {
        String newPhoto = "{\"url\":\"http://example.com/image.jpg\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/photo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPhoto))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"url\":\"http://example.com/image.jpg\"}"));
    }

    @Test
    @Order(2)
    public void testListPhotos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/photo")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"url\":\"http://example.com/image.jpg\"}]"));
    }

    @Test
    @Order(3)
    public void testGetPhotoById() throws Exception {
        // Certifique-se de que existe um ID válido para teste
        mockMvc.perform(MockMvcRequestBuilders.get("/api/photo/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"url\":\"http://example.com/image.jpg\"}"));
    }

    @Test
    @Order(4)
    public void testPhotoAlreadyExists() throws Exception {
        String newPhoto = "{\"url\":\"http://example.com/image.jpg\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/photo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPhoto))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(5)
    public void testUpdatePhoto() throws Exception {
        String updatedPhoto = "{\"url\":\"http://example.com/updated-image.jpg\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/photo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPhoto))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"url\":\"http://example.com/updated-image.jpg\"}"));
    }

    @Test
    @Order(6)
    public void testDeletePhoto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/photo/1"))
                .andDo(result -> System.out.println("Photo deleted"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(7)
    public void testPhotoNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/photo/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


}
