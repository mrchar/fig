package net.mrchar.fig.struct;

import net.mrchar.fig.common.ResourceNotExistsException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class StructService {
  private final StructRepository structRepository;

  public StructEntity addStruct(@Valid StructConcept params) {
    return this.structRepository.save(new StructEntity(params));
  }

  public StructEntity updateStruct(@NotNull Long id, @Valid StructConcept params) {
    StructEntity entity =
        this.structRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotExistsException("Schema not found"));

    entity.setStruct(params);
    return this.structRepository.save(entity);
  }

  public StructEntity deleteStruct(@NotNull Long id) {
    StructEntity entity =
        this.structRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotExistsException("Schema not found"));

    this.structRepository.delete(entity);
    return entity;
  }
}
