package net.mrchar.fig.data;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mrchar.fig.common.AbstractEntity;
import net.mrchar.fig.form.FormEntity;
import org.hibernate.annotations.Type;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "record")
public class RecordEntity extends AbstractEntity {
  @ManyToOne
  @JoinColumn(name = "form_id")
  private FormEntity form;

  @Setter
  @Type(JsonType.class)
  @Column(name = "content", columnDefinition = "json")
  private Object content;

  public RecordEntity(FormEntity form, Object content) {
    this.form = form;
    this.content = content;
  }
}
