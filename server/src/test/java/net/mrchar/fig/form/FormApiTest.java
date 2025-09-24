package net.mrchar.fig.form;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import net.mrchar.fig.mock.FormEntityGenerator;
import net.mrchar.fig.mock.StructEntityGenerator;
import net.mrchar.fig.struct.StructEntity;
import net.mrchar.fig.struct.StructRepository;
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
class FormApiTest {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

  @Autowired StructRepository structRepository;
  @Autowired FormRepository formRepository;
  @Autowired MockMvc mockMvc;

  private StructEntity structEntity;

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
    structEntity = StructEntityGenerator.generate();
    structRepository.save(structEntity);

    List<FormEntity> entities = FormEntityGenerator.generate(structEntity, 10);
    this.formRepository.saveAll(entities);
  }

  @AfterEach
  void tearDown() {
    this.formRepository.deleteAll();
    this.structRepository.deleteAll();
  }

  @Test
  void should_success_when_list_forms() throws Exception {
    mockMvc
        .perform(get("/forms"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  void should_success_when_search_forms() throws Exception {
    FormEntity formEntity = FormEntityGenerator.generate(structEntity);
    formEntity.getForm().setName("name");
    this.formRepository.save(formEntity);

    mockMvc
        .perform(get("/forms?keyword=am"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content").isNotEmpty())
        .andExpect(jsonPath("$.content[0].name").value("name"));
  }

  @Test
  void should_success_when_add_form() throws Exception {
    mockMvc
        .perform(
            post("/forms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "name": "测试",
                              "description": "描述",
                              "structId": %d,
                              "uiSchema": {}
                            }"""
                        .formatted(structEntity.getId())))
        .andExpect(status().isOk());
  }

  @Test
  void should_fail_when_add_form_with_invalid_params() throws Exception {
    mockMvc
        .perform(
            post("/forms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "name": "",
                              "jsonSchema": {},
                              "uiSchema": {}
                            }"""))
        .andExpect(status().isBadRequest());

    mockMvc
        .perform(
            post("/forms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "name": "测试",
                              "jsonSchema": null,
                              "uiSchema": {}
                            }"""))
        .andExpect(status().isBadRequest());

    mockMvc
        .perform(
            post("/forms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "name": "测试",
                              "jsonSchema": {},
                              "uiSchema": null
                            }"""))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_success_when_update_form() throws Exception {
    FormEntity saved = this.formRepository.save(FormEntityGenerator.generate(structEntity));

    mockMvc
        .perform(
            put("/forms/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "name": "修改",
                              "description":"描述",
                              "structId": %d,
                              "uiSchema": {}
                            }"""
                        .formatted(structEntity.getId())))
        .andExpect(status().isOk());
  }

  @Test
  void should_success_when_delete_form() throws Exception {
    FormEntity saved = this.formRepository.save(FormEntityGenerator.generate(structEntity));

    mockMvc.perform(delete("/forms/" + saved.getId())).andExpect(status().isOk());
  }
}
