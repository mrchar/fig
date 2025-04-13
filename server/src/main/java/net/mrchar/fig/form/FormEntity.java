package net.mrchar.fig.form;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mrchar.fig.common.AbstractEntity;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "form")
public class FormEntity extends AbstractEntity {
  @Setter @Embedded @JsonUnwrapped private FormConcept form;

  public FormEntity(FormConcept form) {
    this.form = form;
  }
}
