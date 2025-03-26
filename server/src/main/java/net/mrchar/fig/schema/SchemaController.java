package net.mrchar.fig.schema;

import lombok.RequiredArgsConstructor;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("schemas")
public class SchemaController {
  private final SchemaRepository schemaRepository;
  private final SchemaService schemaService;

  @GetMapping
  public Page<SchemaEntity> listSchemas(Pageable pageable) {
    return this.schemaRepository.findAll(pageable);
  }

  @GetMapping("{id}")
  public SchemaEntity getSchema(@PathVariable Long id) {
    return this.schemaRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotExistsException("Schema not found"));
  }

  @PostMapping
  public SchemaEntity addSchema(@RequestBody SchemaConcept params) {
    return this.schemaService.addSchema(params);
  }

  @PutMapping("{id}")
  public SchemaEntity updateSchema(@PathVariable Long id, @RequestBody SchemaConcept params) {
    return this.schemaService.updateSchema(id, params);
  }

  @DeleteMapping("{id}")
  public SchemaEntity deleteSchema(@PathVariable Long id) {
    return this.schemaService.deleteSchema(id);
  }
}
