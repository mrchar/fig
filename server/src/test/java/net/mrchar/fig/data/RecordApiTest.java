package net.mrchar.fig.data;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import net.mrchar.fig.form.FormEntity;
import net.mrchar.fig.form.FormRepository;
import net.mrchar.fig.mock.FormEntityGenerator;
import net.mrchar.fig.mock.RecordEntityGenerator;
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
class RecordApiTest {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

  @Autowired StructRepository structRepository;
  @Autowired FormRepository formRepository;
  @Autowired RecordRepository recordRepository;
  @Autowired MockMvc mockMvc;

  FormEntity form;

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
    StructEntity structEntity = StructEntityGenerator.generate();
    this.structRepository.save(structEntity);

    FormEntity formEntity = FormEntityGenerator.generate(structEntity);
    this.form = this.formRepository.saveAndFlush(formEntity);

    List<RecordEntity> entities = RecordEntityGenerator.generate(formEntity, 10);
    this.recordRepository.saveAll(entities);
  }

  @AfterEach
  void tearDown() {
    this.recordRepository.deleteAll();
    this.formRepository.deleteAll();
    this.structRepository.deleteAll();
  }

  @Test
  @Order(1)
  void should_success_when_list_records() throws Exception {
    mockMvc
        .perform(get("/records"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  @Order(2)
  void should_success_when_list_records_by_formId() throws Exception {
    mockMvc
        .perform(get("/records?formId=" + this.form.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  @Order(3)
  void should_success_when_add_record() throws Exception {
    mockMvc
        .perform(
            post("/records")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {"formId": %d,
                              "content": {}
                            }"""
                        .formatted(this.form.getId())))
        .andExpect(status().isOk());
  }

  @Test
  @Order(4)
  void should_success_when_update_record() throws Exception {
    RecordEntity record = this.recordRepository.save(RecordEntityGenerator.generate(this.form));

    mockMvc
        .perform(
            put("/records/" + record.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                            {
                                              "content": {}
                                            }"""))
        .andExpect(status().isOk());
  }

  @Test
  @Order(5)
  void should_success_when_delete_record() throws Exception {
    RecordEntity record = this.recordRepository.save(RecordEntityGenerator.generate(this.form));

    mockMvc.perform(delete("/records/" + record.getId())).andExpect(status().isOk());
  }
}
