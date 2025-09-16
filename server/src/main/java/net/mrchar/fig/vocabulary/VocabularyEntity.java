package net.mrchar.fig.vocabulary;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mrchar.fig.common.AbstractEntity;
import net.mrchar.fig.space.SpaceEntity;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "vocabulary")
public class VocabularyEntity extends AbstractEntity {
  @Setter @Embedded @JsonUnwrapped VocabularyConcept vocabulary;

  @ManyToOne
  @JoinColumn(name = "space_id")
  private SpaceEntity space;

  public VocabularyEntity(VocabularyConcept vocabulary, SpaceEntity space) {
    this.vocabulary = vocabulary;
    this.space = space;
  }
}
