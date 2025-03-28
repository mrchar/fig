package net.mrchar.fig.schema;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
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
class SchemaApiTest {
  @Autowired SchemaRepository schemaRepository;
  @Autowired MockMvc mockMvc;

  @BeforeAll
  void setUp() {
    List<SchemaEntity> entities = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      entities.add(SchemaEntityGenerator.generate());
    }
    this.schemaRepository.saveAll(entities);
  }

  @Test
  @Order(1)
  void should_success_when_listing_schemas() throws Exception {
    mockMvc
        .perform(get("/schemas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  @Order(2)
  void should_success_when_adding_schema() throws Exception {
    mockMvc
        .perform(
            post("/schemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"测试\", \"definition\": {}}"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(3)
  void should_fail_when_adding_schema_with_invalid_params() throws Exception {
    mockMvc
        .perform(
            post("/schemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\", \"definition\": {}}"))
        .andDo(print())
        .andExpect(status().isBadRequest());

    mockMvc
        .perform(
            post("/schemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"测试\", \"definition\": null}"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(4)
  void should_success_when_updating_schema() throws Exception {
    mockMvc
        .perform(
            put("/schemas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"修改\", \"definition\": {}}"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(5)
  void should_success_when_deleting_schema() throws Exception {
    mockMvc.perform(delete("/schemas/1")).andExpect(status().isOk());
  }
}
