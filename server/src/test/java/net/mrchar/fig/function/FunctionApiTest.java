package net.mrchar.fig.function;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import net.mrchar.fig.mock.FunctionEntityGenerator;
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
class FunctionApiTest {
  @Autowired FunctionRepository functionRepository;
  @Autowired MockMvc mockMvc;

  ObjectMapper mapper = new ObjectMapper();
  FunctionEntity functionEntity;

  @BeforeAll
  void setUp() {
    List<FunctionEntity> entities = FunctionEntityGenerator.generate(10);
    this.functionRepository.saveAll(entities);

    functionEntity = new FunctionEntity(new FunctionConcept("name", "description", ""));
    functionRepository.save(functionEntity);
  }

  @Test
  @Order(1)
  void should_success_when_list_functions() throws Exception {
    mockMvc
        .perform(get("/functions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  @Order(1)
  void should_success_when_search_with_correct_params() throws Exception {
    mockMvc
        .perform(get("/functions?keyword=name"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty())
        .andExpect(jsonPath("$.content[0].name").value("name"));
  }

  @Test
  @Order(2)
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
  @Order(3)
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
  @Order(4)
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
  @Order(5)
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
  @Order(6)
  void should_success_when_delete_with_correct_params() throws Exception {
    mockMvc.perform(delete("/functions/" + functionEntity.getId())).andExpect(status().isOk());
  }
}
