package net.mrchar.fig.function;

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
@Table(name = "function")
public class FunctionEntity extends AbstractEntity {
  @Setter @Embedded @JsonUnwrapped private FunctionConcept function;

  public FunctionEntity(FunctionConcept function) {
    this.function = function;
  }
}
