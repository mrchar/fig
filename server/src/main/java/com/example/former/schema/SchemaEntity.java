package com.example.former.schema;

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
@Table(name = "schema_definition")
public class SchemaEntity extends AbstractEntity {
  @Setter @Embedded private SchemaConcept schema;

  public SchemaEntity(SchemaConcept schema) {
    this.schema = schema;
  }
}
