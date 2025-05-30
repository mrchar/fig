package net.mrchar.fig.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("vocabularies")
public class VocabularyController {
  private final VocabularyRepository vocabularyRepository;
  private final VocabularyService vocabularyService;

  @Getter
  @AllArgsConstructor
  public static class ListVocabulariesParams {
    private String keyword;
  }

  @GetMapping
  public Page<VocabularyEntity> listVocabularies(ListVocabulariesParams params, Pageable pageable) {
    if (StringUtils.hasText(params.keyword)) {
      return this.vocabularyRepository.searchByNameContainsKeyword(params.getKeyword(), pageable);
    }

    return this.vocabularyRepository.findAll(pageable);
  }

  @GetMapping("{id}")
  public VocabularyEntity getVocabulary(@PathVariable Long id) {
    return this.vocabularyService.getVocabulary(id);
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
