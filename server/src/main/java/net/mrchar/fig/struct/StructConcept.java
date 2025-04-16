package net.mrchar.fig.struct;

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
public class StructConcept {
  @Setter
  @NotBlank(message = "必须为数据模型创建一个名称")
  @Column(name = "name")
  private String name;

  @NotBlank(message = "必须对数据模型进行描述")
  @Column(name = "description")
  private String description;

  @Setter
  @NotNull(message = "必须对数据模型进行定义")
  @Type(JsonType.class)
  @Column(name = "definition", columnDefinition = "json")
  private Object definition;

  public StructConcept(String name, String description, Object definition) {
    this.name = name;
    this.description = description;
    this.definition = definition;
  }
}
