package net.mrchar.fig.vocabulary;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
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
class VocabularyApiTest {
  @Autowired VocabularyRepository vocabularyRepository;
  @Autowired MockMvc mockMvc;

  @BeforeAll
  void setUp() {
    List<VocabularyEntity> entities = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      entities.add(VocabularyEntityGenerator.generate());
    }
    this.vocabularyRepository.saveAll(entities);
  }

  @Test
  @Order(1)
  void should_success_when_listing_vocabularies() throws Exception {
    mockMvc
        .perform(get("/vocabularies"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  @Order(1)
  void should_success_when_search_vocabularies() throws Exception {
    VocabularyEntity entity = new VocabularyEntity();
    entity.setVocabulary(new VocabularyConcept("name", new HashMap<>()));
    this.vocabularyRepository.save(entity);
    mockMvc
        .perform(get("/vocabularies?keyword=am"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty())
        .andExpect(jsonPath("$.content[0].name").value("name"));
  }

  @Test
  @Order(2)
  void should_success_when_create_vocabulary() throws Exception {
    mockMvc
        .perform(
            post("/vocabularies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"测试\", \"definition\": {}}"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(3)
  void should_fail_when_create_vocabulary_with_invalid_params() throws Exception {
    mockMvc
        .perform(
            post("/vocabularies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\", \"definition\": null}"))
        .andExpect(status().isBadRequest());

    mockMvc
        .perform(
            post("/vocabularies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"测试\", \"definition\": null}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(4)
  void should_success_when_update_vocabulary() throws Exception {
    mockMvc
        .perform(
            put("/vocabularies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"修改\", \"definition\": {}}"))
        .andExpect(status().isOk());
  }
}
