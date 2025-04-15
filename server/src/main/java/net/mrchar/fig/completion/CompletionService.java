package net.mrchar.fig.completion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import net.mrchar.fig.common.ExternalServiceException;
import net.mrchar.fig.common.PreparationFailedException;
import net.mrchar.fig.struct.StructRepository;
import net.mrchar.fig.vocabulary.VocabularyEntity;
import net.mrchar.fig.vocabulary.VocabularyRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.util.ResourceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {
  private final VocabularyRepository vocabularyRepository;
  private final StructRepository structRepository;
  private final ChatClient chatClient;

  private final ObjectMapper mapper = new ObjectMapper();
  private final String systemPrompt = ResourceUtils.getText("classpath:/ai/system-prompt.md");

  public CompletionService(
      VocabularyRepository vocabularyRepository,
      StructRepository structRepository,
      ChatClient.Builder chatClientBuilder) {
    this.vocabularyRepository = vocabularyRepository;
    this.structRepository = structRepository;
    this.chatClient = chatClientBuilder.build();
  }

  public Page<SuggestionConcept> getSuggestions(String keyword, Pageable pageable) {
    if ("vocabularies".contains(keyword)) {
      return this.vocabularyRepository
          .findAll(pageable)
          .map(
              (entity) -> {
                try {
                  return SuggestionConcept.builder()
                      .label("vocabularies/" + entity.getVocabulary().getName())
                      .insertText(
                          "\""
                              + entity.getVocabulary().getName()
                              + "\": "
                              + mapper.writeValueAsString(entity.getVocabulary().getDefinition()))
                      .build();
                } catch (JsonProcessingException e) {
                  throw new RuntimeException(e);
                }
              });
    }

    if ("structs".contains(keyword)) {
      return this.structRepository
          .findAll(pageable)
          .map(
              (entity) -> {
                try {
                  return SuggestionConcept.builder()
                      .label("structs/" + entity.getStruct().getName())
                      .insertText(
                          "\""
                              + entity.getStruct().getName()
                              + "\": "
                              + mapper.writeValueAsString(entity.getStruct().getDefinition()))
                      .build();
                } catch (JsonProcessingException e) {
                  throw new RuntimeException(e);
                }
              });
    }

    return Page.empty();
  }

  public String completeVocabulary(String query) {
    String result =
        this.chatClient
            .prompt()
            .system(
"""
你是一个数据建模工程师，你的任务是根据用户的需求，设计一个数据结构用来描述一个简单概念，
它通常不是对象，除非需要多个维度描述这个概念。
为了让用户满意，你必须使用jsonSchema Draft-07进行数据结构定义。
只需要返回定义好的内容就行了，不要返回任何其他内容打扰用户。
""")
            .user(query)
            .call()
            .content();
    if (result == null || !result.startsWith("```json")) {
      throw new ExternalServiceException("大模型返回了错误的结果，请稍候重新尝试。");
    }

    return result.substring(7, result.length() - 3);
  }

  public String completeStruct(String query) {
    List<VocabularyEntity> entities = this.vocabularyRepository.findAll();
    String vocabularies;
    try {
      vocabularies = this.mapper.writeValueAsString(entities);
    } catch (JsonProcessingException e) {
      throw new PreparationFailedException("组装提示词失败，无法调用大模型，请稍候重新尝试。");
    }

    query =
        query
            + """
                        \n下面是我提供的词汇表，请注意并不是所有词汇都有可能用到，请根据需要选择合适的词汇构建我需要的数据结构。
                        """
            + vocabularies;
    String result =
        this.chatClient
            .prompt()
            .system(
"""
你是一个数据建模工程师，你的任务是根据用户的需求，设计出全面且合理的数据结构。
为了让用户满意，你必须使用jsonSchema Draft-07进行数据结构定义。
只需要返回定义好的内容就行了，不要返回任何其他内容打扰用户。
""")
            .user(query)
            .call()
            .content();
    if (result == null || !result.startsWith("```json")) {
      throw new ExternalServiceException("大模型返回了错误的结果，请稍候重新尝试。");
    }

    return result.substring(7, result.length() - 3);
  }

  public String completeForm(String query) {
    String result =
        this.chatClient
            .prompt()
            .system(
"""
你是一个表单设计师，你的任务是根据用户的需求，设计出合理的表单布局。
为了让用户满意，你必须使用JSON Forms组件的的UI Schema语法进行布局设计。
只需要返回定义好的内容就行了，不要返回任何其他内容打扰用户。
""")
            .user(query)
            .call()
            .content();
    if (result == null || !result.startsWith("```json")) {
      throw new ExternalServiceException("大模型返回了错误的结果，请稍候重新尝试。");
    }

    return result.substring(7, result.length() - 3);
  }
}
