package net.mrchar.fig.form;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forms")
public class FormController {
  private final FormRepository formRepository;
  private final FormService formService;

  @GetMapping
  public Page<FormEntity> listForms(Pageable pageable) {
    return this.formRepository.findAll(pageable);
  }

  @GetMapping("{id}")
  public FormEntity getForm(@PathVariable Long id) {
    return this.formService.getForm(id);
  }

  @PostMapping
  public FormEntity addForm(@RequestBody FormConcept params) {
    return this.formService.addForm(params);
  }

  @PutMapping("{id}")
  public FormEntity updateForm(@PathVariable Long id, @RequestBody FormConcept params) {
    return this.formService.updateForm(id, params);
  }

  @DeleteMapping("{id}")
  public FormEntity deleteForm(@PathVariable Long id) {
    return this.formService.deleteForm(id);
  }
}
