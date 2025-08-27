package net.mrchar.fig.form;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import net.mrchar.fig.struct.StructEntity;
import org.hibernate.annotations.Type;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class FormConcept {

  @Setter
  @Column(name = "name")
  @NotBlank(message = "必须为表单创建一个名称")
  private String name;

  @Setter
  @Column(name = "description")
  @NotBlank(message = "必须对表单进行描述")
  private String description;

  @Setter @ManyToOne private StructEntity struct;

  @Setter
  @Type(JsonType.class)
  @Column(name = "ui_schema", columnDefinition = "json")
  private Object uiSchema;

  @Transient
  public Object getJsonSchema() {
    return this.struct.getStruct().getDefinition();
  }
}
