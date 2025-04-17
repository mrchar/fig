package net.mrchar.fig.completion;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
class CompletionControllerTest {
  @Autowired MockMvc mockMvc;
  ObjectMapper mapper = new ObjectMapper();

  @Test
  @Disabled("no mock ai chat client")
  void completeJsonSchema() throws Exception {
    this.mockMvc
        .perform(
            post("/completions/struct")
                .contentType(MediaType.TEXT_PLAIN)
                .content(
                    """
                                请帮我设计一个模型，用来描述在读学生的信息，要包含学生姓名、年龄、性别信息，其中年龄必须是正数，性别只能是男和女。
                                """))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
