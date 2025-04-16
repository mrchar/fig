package net.mrchar.fig.completion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/completions")
public class CompletionController {
  private final CompletionService completionService;

  @GetMapping("/suggestions")
  public Page<SuggestionConcept> getSuggestions(@RequestParam String keyword, Pageable pageable) {
    return completionService.getSuggestions(keyword, pageable);
  }

  @PostMapping("/vocabulary")
  public String completeVocabulary(@RequestBody String query) {
    return this.completionService.completeVocabulary(query);
  }

  @PostMapping("/struct")
  public String completeStructs(@RequestBody String query) {
    return this.completionService.completeStruct(query);
  }

  @PostMapping("/form")
  public String completeForm(@RequestBody String query) {
    return this.completionService.completeForm(query);
  }

  @PostMapping("/function")
  public String completeFunction(@RequestBody String query) {
    return this.completionService.completeFunction(query);
  }
}
