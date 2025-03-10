package com.example.former.form;

import com.example.former.common.AbstractEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "form")
public class FormEntity extends AbstractEntity {
  @Setter @Embedded private FormConcept form;

  public FormEntity(FormConcept form) {
    this.form = form;
  }
}
