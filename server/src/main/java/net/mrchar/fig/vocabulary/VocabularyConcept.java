package net.mrchar.fig.vocabulary;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Getter
@Embeddable
@NoArgsConstructor
public class VocabularyConcept {
  @NotBlank
  @Column(name = "name")
  private String name;

  @NotNull
  @Type(JsonType.class)
  @Column(name = "definition", columnDefinition = "json")
  private Object definition;

  public VocabularyConcept(String name, Object definition) {
    this.name = name;
    this.definition = definition;
  }
}
