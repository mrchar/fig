package net.mrchar.fig.vocabulary;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class VocabularyService {
  private final VocabularyRepository vocabularyRepository;

  public VocabularyEntity addVocabulary(@Valid VocabularyConcept vocabulary) {
    VocabularyEntity entity = new VocabularyEntity(vocabulary);
    return this.vocabularyRepository.save(entity);
  }

  public VocabularyEntity updateVocabulary(@NotNull Long id, @Valid VocabularyConcept vocabulary) {
    VocabularyEntity entity =
        this.vocabularyRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotExistsException(
                        String.format("Vocabulary with id %s not found", id)));

    entity.setVocabulary(vocabulary);

    return this.vocabularyRepository.save(entity);
  }

  public VocabularyEntity deleteVocabulary(@Valid Long id) {
    VocabularyEntity entity =
        this.vocabularyRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotExistsException(
                        String.format("Vocabulary with id %s not found", id)));

    this.vocabularyRepository.delete(entity);
    return entity;
  }
}
