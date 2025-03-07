package com.example.former.vocabulary;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Getter
@Embeddable
@NoArgsConstructor
public class VocabularyConcept {
  @Column(name = "name")
  private String name;

  @Type(JsonType.class)
  @Column(name = "definition", columnDefinition = "json")
  private Object definition;

  public VocabularyConcept(String name, Object definition) {
    this.name = name;
    this.definition = definition;
  }
}
