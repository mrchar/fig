package net.mrchar.fig.completion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompletionController {
  private final CompletionService completionService;

  @GetMapping("/suggestions")
  public Page<SuggestionConcept> getSuggestions(@RequestParam String keyword, Pageable pageable) {
    return completionService.getSuggestions(keyword, pageable);
  }
}
