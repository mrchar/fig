package com.example.former.vocabulary;

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
@Table(name = "vocabulary")
public class VocabularyEntity extends AbstractEntity {
  @Setter @Embedded VocabularyConcept vocabulary;

  public VocabularyEntity(VocabularyConcept vocabulary) {
    this.vocabulary = vocabulary;
  }
}
