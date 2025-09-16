package net.mrchar.fig.vocabulary;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("vocabularies")
public class VocabularyController {
  private final VocabularyService vocabularyService;

  @GetMapping
  public Page<VocabularyEntity> listVocabularies(
      VocabularyService.ListVocabulariesParams params, Pageable pageable) {
    return vocabularyService.listVocabulariesSpace(params, pageable);
  }

  @GetMapping("{id}")
  public VocabularyEntity getVocabulary(@PathVariable Long id) {
    return this.vocabularyService.getVocabulary(id);
  }

  @PostMapping
  public VocabularyEntity addVocabulary(
      @RequestBody VocabularyService.AddVocabularyParams request) {
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
