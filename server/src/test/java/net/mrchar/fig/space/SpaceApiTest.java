package net.mrchar.fig.space;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@WithUserDetails("username")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class SpaceApiTest {
  @Autowired UserRepository userRepository;
  @Autowired SpaceRepository spaceRepository;
  @Autowired MockMvc mockMvc;

  ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
  String code;

  @BeforeAll
  void setup() {
    List<UserEntity> users = this.userRepository.findAll();
    List<SpaceEntity> entities = SpaceEntityGenerator.generate(users.get(0), 10);
    this.spaceRepository.saveAll(entities);
  }

  @Test
  @Order(1)
  void should_success_when_list_spaces() throws Exception {
    mockMvc
        .perform(get("/spaces"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  @Order(2)
  void should_success_when_add_space() throws Exception {
    mockMvc
        .perform(
            post("/spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Map.of("name", "name"))))
        .andExpect(status().isOk())
        .andDo(
            result -> {
              SpaceEntity entity =
                  mapper.readValue(result.getResponse().getContentAsString(), SpaceEntity.class);
              code = entity.getCode();
            });
  }

  @Test
  @Order(3)
  void should_success_when_update_space() throws Exception {
    mockMvc
        .perform(
            put("/spaces/" + code)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Map.of("name", "changed"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("changed"));
  }

  @Test
  @Order(4)
  void should_success_when_delete_space() throws Exception {
    mockMvc.perform(delete("/spaces/" + code)).andExpect(status().isOk());
  }
}
