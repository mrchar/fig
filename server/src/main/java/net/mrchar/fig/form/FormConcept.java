package net.mrchar.fig.form;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mrchar.fig.struct.StructEntity;
import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

@Getter
@Embeddable
@NoArgsConstructor
public class FormConcept {
  @NotBlank
  @Setter
  @Column(name = "name")
  private String name;

  @Setter
  @Column(name = "description")
  private String description;

  @Setter @NonNull @ManyToOne private StructEntity struct;

  @NotNull
  @Setter
  @Type(JsonType.class)
  @Column(name = "ui_schema", columnDefinition = "json")
  private Object uiSchema;

  public FormConcept(String name, String description, StructEntity struct, Object uiSchema) {
    this.name = name;
    this.description = description;
    this.struct = struct;
    this.uiSchema = uiSchema;
  }

  @Transient
  public Object getJsonSchema() {
    return this.struct.getStruct().getDefinition();
  }
}
