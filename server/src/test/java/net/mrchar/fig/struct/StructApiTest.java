package net.mrchar.fig.struct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import net.mrchar.fig.mock.StructEntityGenerator;
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
class StructApiTest {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

  @Autowired StructRepository structRepository;
  @Autowired MockMvc mockMvc;

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
    List<StructEntity> entities = StructEntityGenerator.generate(10);
    this.structRepository.saveAll(entities);
  }

  @AfterEach
  void tearDown() {
    this.structRepository.deleteAll();
  }

  @Test
  void should_success_when_listing_structs() throws Exception {
    mockMvc
        .perform(get("/structs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  void should_success_when_search_structs() throws Exception {
    StructEntity entity = new StructEntity();
    entity.setStruct(new StructConcept("name", "description", new HashMap<>()));
    this.structRepository.save(entity);
    mockMvc
        .perform(get("/structs?keyword=am"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  void should_success_when_adding_struct() throws Exception {
    mockMvc
        .perform(
            post("/structs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                          "name": "测试",
                          "description": "描述",
                          "definition": {}
                        }
                        """))
        .andExpect(status().isOk());
  }

  @Test
  void should_fail_when_adding_struct_with_invalid_params() throws Exception {
    mockMvc
        .perform(
            post("/structs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\", \"definition\": {}}"))
        .andDo(print())
        .andExpect(status().isBadRequest());

    mockMvc
        .perform(
            post("/structs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"测试\", \"definition\": null}"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_success_when_updating_struct() throws Exception {
    StructEntity structEntity = this.structRepository.save(StructEntityGenerator.generate());

    mockMvc
        .perform(
            put("/structs/" + structEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                          "name": "修改",
                          "description": "描述",
                          "definition": {}
                        }"""))
        .andExpect(status().isOk());
  }

  @Test
  void should_success_when_deleting_struct() throws Exception {
    StructEntity structEntity = this.structRepository.save(StructEntityGenerator.generate());

    mockMvc.perform(delete("/structs/" + structEntity.getId())).andExpect(status().isOk());
  }
}
