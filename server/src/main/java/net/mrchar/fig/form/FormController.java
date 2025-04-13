package net.mrchar.fig.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forms")
public class FormController {
  private final FormRepository formRepository;
  private final FormService formService;

  @Getter
  @AllArgsConstructor
  public static class ListFormsParams {
    private String keyword;
  }

  @GetMapping
  public Page<FormEntity> listForms(ListFormsParams params, Pageable pageable) {
    if (StringUtils.hasText(params.keyword)) {
      return this.formRepository.searchByNameLikeKeyword(params.getKeyword(), pageable);
    }
    return this.formRepository.findAll(pageable);
  }

  @GetMapping("{id}")
  public FormEntity getForm(@PathVariable Long id) {
    return this.formService.getForm(id);
  }

  @Getter
  @Setter
  @JsonIgnoreProperties("struct")
  public static class AddOrUpdateFormParams {
    private Long structId;
    @JsonUnwrapped private FormConcept form;
  }

  @PostMapping
  public FormEntity addForm(@RequestBody AddOrUpdateFormParams params) {
    return this.formService.addForm(params.getStructId(), params.getForm());
  }

  @PutMapping("{id}")
  public FormEntity updateForm(@PathVariable Long id, @RequestBody AddOrUpdateFormParams params) {
    return this.formService.updateForm(id, params.getStructId(), params.getForm());
  }

  @DeleteMapping("{id}")
  public FormEntity deleteForm(@PathVariable Long id) {
    return this.formService.deleteForm(id);
  }
}
