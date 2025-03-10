package com.example.former.schema;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Embeddable
@NoArgsConstructor
public class SchemaConcept {
  @Setter
  @NotBlank
  @Column(name = "name")
  private String name;

  @Setter
  @NotNull
  @Type(JsonType.class)
  @Column(name = "definition", columnDefinition = "json")
  private Object definition;

  public SchemaConcept(String name, Object definition) {
    this.name = name;
    this.definition = definition;
  }
}
