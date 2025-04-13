package net.mrchar.fig.data;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import net.mrchar.fig.form.FormEntity;
import net.mrchar.fig.form.FormEntityGenerator;
import net.mrchar.fig.form.FormRepository;
import net.mrchar.fig.struct.StructEntity;
import net.mrchar.fig.struct.StructEntityGenerator;
import net.mrchar.fig.struct.StructRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class RecordApiTest {
  @Autowired StructRepository structRepository;
  @Autowired FormRepository formRepository;
  @Autowired RecordRepository recordRepository;
  @Autowired MockMvc mockMvc;

  private FormEntity formEntity;

  @BeforeAll
  void setUp() {
    StructEntity structEntity = StructEntityGenerator.generate();
    this.structRepository.save(structEntity);
    formEntity = FormEntityGenerator.generate(structEntity);
    formEntity = this.formRepository.saveAndFlush(formEntity);

    List<RecordEntity> entities = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      entities.add(RecordEntityGenerator.generate(formEntity));
    }

    this.recordRepository.saveAll(entities);
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
        .perform(get("/records?formId=" + formEntity.getId()))
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
                            {"formId": 1,
                              "content": {}
                            }"""))
        .andExpect(status().isOk());
  }

  @Test
  @Order(4)
  void should_success_when_update_record() throws Exception {
    mockMvc
        .perform(
            put("/records/" + formEntity.getId())
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
    mockMvc.perform(delete("/records/" + formEntity.getId())).andExpect(status().isOk());
  }
}
