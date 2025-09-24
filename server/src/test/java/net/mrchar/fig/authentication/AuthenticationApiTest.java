package net.mrchar.fig.authentication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Map;
import net.datafaker.Faker;
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
class AuthenticationApiTest {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
  static Faker FAKER = new Faker();

  @Autowired UserRepository userRepository;
  @Autowired MockMvc mockMvc;

  ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

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
  void setUp() {
    List<UserEntity> entities = UserEntityGenerator.generate(10);
    this.userRepository.saveAll(entities);
  }

  @AfterEach
  void tearDown() {
    this.userRepository.deleteAll();
  }

  @Test
  void should_success_when_list_users() throws Exception {
    mockMvc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  void should_success_when_add_user() throws Exception {
    mockMvc
        .perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Map.of("username", FAKER.name().fullName()))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").isString())
        .andExpect(jsonPath("$.password").doesNotExist());
  }

  @Test
  void should_success_when_update_user() throws Exception {
    UserEntity userEntity = this.userRepository.save(UserEntityGenerator.generate("username"));

    mockMvc
        .perform(
            put("/users/" + userEntity.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Map.of("username", FAKER.name().fullName()))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").isString())
        .andExpect(jsonPath("$.password").doesNotExist());
  }

  @Test
  void should_success_when_delete_user() throws Exception {
    UserEntity userEntity = this.userRepository.save(UserEntityGenerator.generate("username"));

    mockMvc.perform(delete("/users/" + userEntity.getCode())).andExpect(status().isOk());
  }
}
