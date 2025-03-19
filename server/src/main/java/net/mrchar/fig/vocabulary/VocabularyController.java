package net.mrchar.fig.vocabulary;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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

  public static class AddOrUpdateVocabularyParams extends VocabularyConcept {
    @Override
    @NotBlank
    public String getName() {
      return super.getName();
    }

    @Override
    @NotNull
    public Object getDefinition() {
      return super.getDefinition();
    }
  }

  @PostMapping
  public VocabularyEntity addVocabulary(@RequestBody AddOrUpdateVocabularyParams request) {
    return this.vocabularyService.addVocabulary(request);
  }

  @PutMapping("{id}")
  public VocabularyEntity updateVocabulary(
      @PathVariable Long id, @RequestBody AddOrUpdateVocabularyParams request) {
    return this.vocabularyService.updateVocabulary(id, request);
  }

  @DeleteMapping("{id}")
  public VocabularyEntity deleteVocabulary(@PathVariable Long id) {
    return this.vocabularyService.deleteVocabulary(id);
  }
}
