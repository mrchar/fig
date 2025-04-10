package net.mrchar.fig.completion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.schema.SchemaRepository;
import net.mrchar.fig.vocabulary.VocabularyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompletionService {
  private final VocabularyRepository vocabularyRepository;
  private final SchemaRepository schemaRepository;

  private final ObjectMapper mapper = new ObjectMapper();

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

    if ("schemas".contains(keyword)) {
      return this.schemaRepository
          .findAll(pageable)
          .map(
              (entity) -> {
                try {
                  return SuggestionConcept.builder()
                      .label("schemas/" + entity.getSchema().getName())
                      .insertText(
                              "\""
                              + entity.getSchema().getName()
                              + "\": "
                              + mapper.writeValueAsString(entity.getSchema().getDefinition()))
                      .build();
                } catch (JsonProcessingException e) {
                  throw new RuntimeException(e);
                }
              });
    }

    return Page.empty();
  }
}
