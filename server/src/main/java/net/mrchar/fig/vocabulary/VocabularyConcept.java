package net.mrchar.fig.vocabulary;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mrchar.fig.space.SpaceEntity;
import org.hibernate.annotations.Type;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyConcept {
  @NotBlank(message = "必须为词汇定义标识")
  @Column(name = "key")
  private String key;

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
}
