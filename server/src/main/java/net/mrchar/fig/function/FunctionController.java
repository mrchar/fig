package net.mrchar.fig.function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/functions")
public class FunctionController {
  private final FunctionRepository functionRepository;
  private final FunctionService functionService;

  @Getter
  @AllArgsConstructor
  public static class ListFUnctionsParams {
    private String keyword;
  }

  @GetMapping
  public Page<FunctionEntity> listFunctions(ListFUnctionsParams params, Pageable pageable) {
    if (params.keyword != null) {
      return this.functionRepository.searchByCodeOrNameLikeKeyword(params.keyword, pageable);
    }
    return this.functionRepository.findAll(pageable);
  }

  @GetMapping("{id}")
  public FunctionEntity getFunction(@PathVariable Long id) {
    return this.functionRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotExistsException("Function not found"));
  }

  @PostMapping
  public FunctionEntity addFunction(@RequestBody FunctionConcept function) {
    return this.functionService.addFunction(function);
  }

  @PutMapping("{id}")
  public FunctionEntity updateFunction(
      @PathVariable Long id, @RequestBody FunctionConcept concept) {
    return this.functionService.updateFunction(id, concept);
  }

  @DeleteMapping("{id}")
  public FunctionEntity deleteFunction(@PathVariable Long id) {
    return this.functionService.deleteFunction(id);
  }
}
