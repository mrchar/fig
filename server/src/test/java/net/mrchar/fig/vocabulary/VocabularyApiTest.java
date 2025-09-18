package net.mrchar.fig.vocabulary;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.mrchar.fig.authentication.UserEntity;
import net.mrchar.fig.authentication.UserRepository;
import net.mrchar.fig.mock.SpaceEntityGenerator;
import net.mrchar.fig.mock.UserEntityGenerator;
import net.mrchar.fig.mock.VocabularyEntityGenerator;
import net.mrchar.fig.space.SpaceEntity;
import net.mrchar.fig.space.SpaceRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
class VocabularyApiTest {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
  static ObjectMapper mapper = new ObjectMapper();

  @Autowired UserRepository userRepository;
  @Autowired SpaceRepository spaceRepository;
  @Autowired VocabularyRepository vocabularyRepository;
  @Autowired MockMvc mockMvc;

  UserEntity user;
  SpaceEntity space;
  VocabularyEntity vocabulary;

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
    this.vocabularyRepository.deleteAll();
    this.spaceRepository.deleteAll();
    this.userRepository.deleteAll();

    this.user = this.userRepository.save(UserEntityGenerator.generate("user"));
    this.space = this.spaceRepository.save(SpaceEntityGenerator.generate(user));

    this.vocabulary = vocabularyRepository.save(VocabularyEntityGenerator.generate(space));

    List<VocabularyEntity> vocabularies = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      vocabularies.add(VocabularyEntityGenerator.generate(space));
    }
    this.vocabularyRepository.saveAll(vocabularies);
  }

  @Test
  void should_success_when_listing_vocabularies() throws Exception {
    mockMvc
        .perform(get("/vocabularies?space={space}", space.getCode()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  void should_success_when_search_vocabularies() throws Exception {
    VocabularyEntity entity =
        new VocabularyEntity(
            new VocabularyConcept("key", "justaname", "description", new HashMap<>()), space);
    this.vocabularyRepository.save(entity);
    mockMvc
        .perform(get("/vocabularies?space={space}&keyword=ustaname", space.getCode()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty())
        .andExpect(jsonPath("$.content[0].name").value("justaname"));
  }

  @Test
  void should_success_when_create_vocabulary() throws Exception {
    VocabularyService.AddVocabularyParams params =
        new VocabularyService.AddVocabularyParams(
            VocabularyConcept.builder()
                .key("key")
                .name("测试")
                .description("描述")
                .definition(new HashMap<>())
                .build(),
            space.getCode());
    mockMvc
        .perform(
            post("/vocabularies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(params)))
        .andExpect(status().isOk());
  }

  @Test
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
  void should_success_when_update_vocabulary() throws Exception {
    mockMvc
        .perform(
            put("/vocabularies/" + this.vocabulary.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "key": "key",
                              "name": "修改",
                              "description": "描述",
                              "definition": {}
                            }"""))
        .andExpect(status().isOk());
  }
}
