package net.mrchar.fig.struct;

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
@Table(name = "struct")
public class StructEntity extends AbstractEntity {
  @JsonUnwrapped @Setter @Embedded private StructConcept struct;

  public StructEntity(StructConcept struct) {
    this.struct = struct;
  }
}
