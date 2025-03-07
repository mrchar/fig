package com.example.former.vocabulary;

import com.example.former.common.ResourceNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VocabularyService {
  private final VocabularyRepository vocabularyRepository;

  public VocabularyEntity addVocabulary(VocabularyConcept vocabulary) {
    VocabularyEntity entity = new VocabularyEntity(vocabulary);
    return this.vocabularyRepository.save(entity);
  }

  public VocabularyEntity updateVocabulary(Long id, VocabularyConcept vocabulary) {
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

  public VocabularyEntity deleteVocabulary(Long id) {
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
