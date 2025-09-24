package net.mrchar.fig.space;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Map;
import net.mrchar.fig.authentication.UserEntity;
import net.mrchar.fig.authentication.UserRepository;
import net.mrchar.fig.mock.SpaceEntityGenerator;
import net.mrchar.fig.mock.UserEntityGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
class SpaceApiTest {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

  @Autowired UserRepository userRepository;
  @Autowired SpaceRepository spaceRepository;
  @Autowired MockMvc mockMvc;

  ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
  SpaceEntity space;

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  static void dynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @BeforeEach
  void setup() {
    UserEntity user = this.userRepository.save(UserEntityGenerator.generate("user"));

    space = this.spaceRepository.save(SpaceEntityGenerator.generate(user));

    List<SpaceEntity> entities = SpaceEntityGenerator.generate(user, 10);
    this.spaceRepository.saveAll(entities);
  }

  @AfterEach
  void tearDown() {
    this.spaceRepository.deleteAll();
    this.userRepository.deleteAll();
  }

  @Test
  void should_success_when_list_spaces() throws Exception {
    mockMvc
        .perform(get("/spaces"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  void should_success_when_add_space() throws Exception {
    mockMvc
        .perform(
            post("/spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Map.of("name", "name"))))
        .andExpect(status().isOk());
  }

  @Test
  void should_success_when_update_space() throws Exception {
    mockMvc
        .perform(
            put("/spaces/" + this.space.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Map.of("name", "changed"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("changed"));
  }

  @Test
  void should_success_when_delete_space() throws Exception {
    mockMvc.perform(delete("/spaces/" + this.space.getCode())).andExpect(status().isOk());
  }
}
