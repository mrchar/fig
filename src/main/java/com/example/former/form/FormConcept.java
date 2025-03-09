package com.example.former.form;

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
public class FormConcept {
  @NotBlank
  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @NotNull
  @Type(JsonType.class)
  @Column(name = "json_schema", columnDefinition = "json")
  private Object jsonSchema;

  @NotNull
  @Type(JsonType.class)
  @Column(name = "ui_schema", columnDefinition = "json")
  private Object uiSchema;

  public FormConcept(String name, String description, Object jsonSchema, Object uiSchema) {
    this.name = name;
    this.description = description;
    this.jsonSchema = jsonSchema;
    this.uiSchema = uiSchema;
  }
}
