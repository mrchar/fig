package net.mrchar.fig.completion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompletionControllerTest {
  @Autowired MockMvc mockMvc;

  @Test
  void completeJsonSchema() throws Exception {
    this.mockMvc
        .perform(
            post("/json-schema")
                .contentType(MediaType.TEXT_PLAIN)
                .content(
                    """
                                请帮我设计一个模型，用来描述在读学生的信息，要包含学生姓名、年龄、性别信息，其中年龄必须是正数，性别只能是男和女。
                                """))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            result -> {
              assert result.getResponse().getContentAsString().startsWith("```json");
            });
  }
}
