package net.mrchar.fig.function;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class FunctionService {
  private final FunctionRepository functionRepository;

  public FunctionEntity addFunction(@Valid FunctionConcept concept) {
    FunctionEntity entity = new FunctionEntity(concept);
    return this.functionRepository.save(entity);
  }

  public FunctionEntity updateFunction(@NotNull Long id, @Valid FunctionConcept concept) {
    FunctionEntity entity =
        this.functionRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotExistsException("Function not found"));
    entity.setFunction(concept);
    return this.functionRepository.save(entity);
  }

  public FunctionEntity deleteFunction(@NotNull Long id) {
    FunctionEntity entity =
        this.functionRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotExistsException("Function not found"));
    this.functionRepository.delete(entity);
    return entity;
  }
}
