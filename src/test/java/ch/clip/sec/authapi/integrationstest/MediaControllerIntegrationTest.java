package ch.clip.sec.authapi.integrationstest;

import ch.clip.sec.authapi.model.User;
import ch.clip.sec.authapi.repo.MediaRepository;
import ch.clip.sec.authapi.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootApplication(scanBasePackages = "ch.clip.sec.authapi")
@EntityScan(basePackages = "ch.clip.sec.authapi.model")
class MediaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UserRepository userRepository;

    private String jwtToken;

    @BeforeEach
    void setup() throws Exception {
        mediaRepository.deleteAll();
        userRepository.deleteAll();

        // Registrierung eines neuen Benutzers
        String signupBody = """
            {
                "email": "integration@example.com",
                "password": "password123"
            }
        """;
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupBody))
                .andExpect(status().isOk());

        // Login und Token speichern
        String signinBody = """
            {
                "email": "integration@example.com",
                "password": "password123"
            }
        """;
        MvcResult result = mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signinBody))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        jwtToken = json.split("token\":\"")[1].split("\"")[0];
    }

    @Test
    void testCreateAndFetchMedia() throws Exception {
        String mediaJson = """
            {
                "type": "post",
                "content": "Hello from integration test with token"
            }
        """;

        mockMvc.perform(post("/api/media")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(mediaJson))
                .andExpect(status().isOk());

        var all = mediaRepository.findAll();
        assertEquals(1, all.size());
        assertEquals("Hello from integration test with token", all.get(0).getContent());
    }
}
