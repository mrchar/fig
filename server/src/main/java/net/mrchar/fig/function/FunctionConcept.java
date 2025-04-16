package net.mrchar.fig.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Embeddable
@NoArgsConstructor
public class FunctionConcept {
  @Setter
  @NotBlank
  @JsonProperty("name")
  @Column(name = "name")
  private String name;

  @Setter
  @JsonProperty("description")
  @Column(name = "description")
  private String description;

  @Setter
  @NotBlank
  @JsonProperty("content")
  @Column(name = "content")
  private String content;

  public FunctionConcept(String name, String description, String content) {
    this.name = name;
    this.description = description;
    this.content = content;
  }
}
