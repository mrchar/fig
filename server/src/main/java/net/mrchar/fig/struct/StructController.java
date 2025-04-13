package net.mrchar.fig.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("structs")
public class StructController {
  private final StructRepository structRepository;
  private final StructService structService;

  @Getter
  @AllArgsConstructor
  public static class ListStructsParams {
    public String keyword;
  }

  @GetMapping
  public Page<StructEntity> listStructs(ListStructsParams params, Pageable pageable) {
    if (StringUtils.hasText(params.keyword)) {
      return this.structRepository.searchByNameContainsKeyword(params.getKeyword(), pageable);
    }
    return this.structRepository.findAll(pageable);
  }

  @GetMapping("{id}")
  public StructEntity getStruct(@PathVariable Long id) {
    return this.structRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotExistsException("Schema not found"));
  }

  @PostMapping
  public StructEntity addStruct(@RequestBody StructConcept params) {
    return this.structService.addStruct(params);
  }

  @PutMapping("{id}")
  public StructEntity updateStruct(@PathVariable Long id, @RequestBody StructConcept params) {
    return this.structService.updateStruct(id, params);
  }

  @DeleteMapping("{id}")
  public StructEntity deleteStruct(@PathVariable Long id) {
    return this.structService.deleteStruct(id);
  }
}
