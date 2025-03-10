package com.example.former.data;

import com.example.former.common.AbstractEntity;
import com.example.former.form.FormEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
