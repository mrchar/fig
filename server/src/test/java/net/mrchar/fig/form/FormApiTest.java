package net.mrchar.fig.form;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import net.mrchar.fig.mock.FormEntityGenerator;
import net.mrchar.fig.mock.StructEntityGenerator;
import net.mrchar.fig.struct.StructEntity;
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
class FormApiTest {
  @Autowired StructRepository structRepository;
  @Autowired FormRepository formRepository;
  @Autowired MockMvc mockMvc;

  private StructEntity structEntity;

  @BeforeAll
  void setUp() {
    structEntity = StructEntityGenerator.generate();
    structRepository.save(structEntity);

    ArrayList<FormEntity> entities = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      entities.add(FormEntityGenerator.generate(structEntity));
    }

    this.formRepository.saveAll(entities);
  }

  @Test
  @Order(1)
  void should_success_when_list_forms() throws Exception {
    mockMvc.perform(get("/forms")).andExpect(status().isOk());
  }

  @Test
  @Order(1)
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
  @Order(2)
  void should_success_when_add_form() throws Exception {
    mockMvc
        .perform(
            post("/forms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "name": "测试",
                              "structId": %d,
                              "uiSchema": {}
                            }"""
                        .formatted(structEntity.getId())))
        .andExpect(status().isOk());
  }

  @Test
  @Order(3)
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
  @Order(4)
  void should_success_when_update_form() throws Exception {
    mockMvc
        .perform(
            put("/forms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "name": "修改",
                              "structId": %d,
                              "uiSchema": {}
                            }"""
                        .formatted(structEntity.getId())))
        .andExpect(status().isOk());
  }

  @Test
  @Order(5)
  void should_success_when_delete_form() throws Exception {
    mockMvc.perform(delete("/forms/1")).andExpect(status().isOk());
  }
}
