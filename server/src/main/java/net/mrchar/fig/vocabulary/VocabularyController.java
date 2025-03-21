package net.mrchar.fig.vocabulary;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("vocabularies")
public class VocabularyController {
  private final VocabularyRepository vocabularyRepository;
  private final VocabularyService vocabularyService;

  @GetMapping
  public Page<VocabularyEntity> listVocabularies(Pageable pageable) {
    return this.vocabularyRepository.findAll(pageable);
  }

  @GetMapping("{id}")
  public VocabularyEntity getVocabulary(@PathVariable Long id) {
    return this.vocabularyRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotExistsException("Vocabulary not found"));
  }

  @PostMapping
  public VocabularyEntity addVocabulary(@RequestBody VocabularyConcept request) {
    return this.vocabularyService.addVocabulary(request);
  }

  @PutMapping("{id}")
  public VocabularyEntity updateVocabulary(
      @PathVariable Long id, @RequestBody VocabularyConcept request) {
    return this.vocabularyService.updateVocabulary(id, request);
  }

  @DeleteMapping("{id}")
  public VocabularyEntity deleteVocabulary(@PathVariable Long id) {
    return this.vocabularyService.deleteVocabulary(id);
  }
}
