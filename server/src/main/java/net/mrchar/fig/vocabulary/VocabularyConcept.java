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
  @NotBlank(message = "必须指明要定义的词汇！")
  @Column(name = "name")
  private String name;

  @NotBlank(message = "必须对词汇的含义进行解释！")
  @Column(name = "description")
  private String description;

  @NotNull(message = "必须使用建模语言对词汇进行定义")
  @Type(JsonType.class)
  @Column(name = "definition", columnDefinition = "json")
  private Object definition;

  public VocabularyConcept(String name, String description, Object definition) {
    this.name = name;
    this.description = description;
    this.definition = definition;
  }
}
