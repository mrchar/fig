package net.mrchar.fig.function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import net.mrchar.fig.mock.FunctionEntityGenerator;
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
class FunctionApiTest {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

  @Autowired FunctionRepository functionRepository;
  @Autowired MockMvc mockMvc;

  ObjectMapper mapper = new ObjectMapper();
  FunctionEntity function;

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
    List<FunctionEntity> entities = FunctionEntityGenerator.generate(10);
    this.functionRepository.saveAll(entities);

    FunctionEntity functionEntity =
        new FunctionEntity(new FunctionConcept("name", "description", ""));
    this.function = functionRepository.save(functionEntity);
  }

  @AfterEach
  void tearDown() {
    this.functionRepository.deleteAll();
  }

  @Test
  void should_success_when_list_functions() throws Exception {
    mockMvc
        .perform(get("/functions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  void should_success_when_search_with_correct_params() throws Exception {
    mockMvc
        .perform(get("/functions?keyword=name"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty())
        .andExpect(jsonPath("$.content[0].name").value("name"));
  }

  @Test
  void should_success_when_create_with_correct_params() throws Exception {
    FunctionEntity entity = FunctionEntityGenerator.generate();
    mockMvc
        .perform(
            post("/functions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(entity)))
        .andExpect(status().isOk());
  }

  @Test
  void should_fail_when_create_with_incorrect_params() throws Exception {
    FunctionEntity entity = FunctionEntityGenerator.generate();
    entity.getFunction().setName(null);
    mockMvc
        .perform(
            post("/functions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(entity)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_success_when_update_with_correct_params() throws Exception {
    FunctionEntity entity = FunctionEntityGenerator.generate();
    this.functionRepository.save(entity);

    entity.getFunction().setName("newName");
    mockMvc
        .perform(
            put("/functions/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(entity.getFunction())))
        .andExpect(status().isOk());
  }

  @Test
  void should_fail_when_update_with_incorrect_params() throws Exception {
    FunctionEntity entity = FunctionEntityGenerator.generate();
    this.functionRepository.save(entity);

    entity.getFunction().setName(null);
    mockMvc
        .perform(
            put("/functions/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(entity.getFunction())))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_success_when_delete_with_correct_params() throws Exception {
    mockMvc.perform(delete("/functions/" + function.getId())).andExpect(status().isOk());
  }
}
