package com.example.former.schema;

import com.example.former.common.ResourceNotExistsException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class SchemaService {
  private final SchemaRepository schemaRepository;

  public SchemaEntity addSchema(@Valid SchemaConcept params) {
    return this.schemaRepository.save(new SchemaEntity(params));
  }

  public SchemaEntity updateSchema(@NotNull Long id, @Valid SchemaConcept params) {
    SchemaEntity entity =
        this.schemaRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotExistsException("Schema not found"));

    entity.setSchema(params);
    return this.schemaRepository.save(entity);
  }

  public SchemaEntity deleteSchema(@NotNull Long id) {
    SchemaEntity entity =
        this.schemaRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotExistsException("Schema not found"));

    this.schemaRepository.delete(entity);
    return entity;
  }
}
