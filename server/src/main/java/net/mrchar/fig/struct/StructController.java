package net.mrchar.fig.struct;

import lombok.RequiredArgsConstructor;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("structs")
public class StructController {
  private final StructRepository structRepository;
  private final StructService structService;

  @GetMapping
  public Page<StructEntity> listStructs(Pageable pageable) {
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
