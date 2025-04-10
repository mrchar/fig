package net.mrchar.fig.completion;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuggestionConcept {
  private String label;
  private String insertText;
  private String detail;
  private String documentation;
}
