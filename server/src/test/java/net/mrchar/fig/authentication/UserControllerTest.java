package net.mrchar.fig.authentication;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
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
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class UserControllerTest {
  static Faker FAKER = new Faker();

  @Autowired UserRepository userRepository;
  @Autowired MockMvc mockMvc;

  ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
  String code;

  @BeforeAll
  void setUp() {
    List<UserEntity> entities = UserEntityGenerator.generate(10);
    this.userRepository.saveAll(entities);
  }

  @Test
  @Order(1)
  void should_success_when_list_users() throws Exception {
    mockMvc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  @Order(2)
  void should_success_when_add_user() throws Exception {
    mockMvc
        .perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Map.of("username", FAKER.name().fullName()))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").isString())
        .andExpect(jsonPath("$.password").doesNotExist())
        .andDo(
            result -> {
              UserEntity entity =
                  mapper.readValue(result.getResponse().getContentAsString(), UserEntity.class);
              code = entity.getCode();
            });
  }

  @Test
  @Order(3)
  void should_success_when_update_user() throws Exception {
    mockMvc
        .perform(
            put("/users/" + code)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Map.of("username", FAKER.name().fullName()))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").isString())
        .andExpect(jsonPath("$.password").doesNotExist());
  }

  @Test
  @Order(4)
  void should_success_when_delete_user() throws Exception {
    mockMvc.perform(delete("/users/" + code)).andExpect(status().isOk());
  }
}
