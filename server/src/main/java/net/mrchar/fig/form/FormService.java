package net.mrchar.fig.form;

import net.mrchar.fig.common.ResourceNotExistsException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class FormService {
  private final FormRepository formRepository;

  public FormEntity getForm(@NotNull Long id) {
    return this.formRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotExistsException("Form does not exist"));
  }

  public FormEntity addForm(@Valid FormConcept form) {
    FormEntity entity = new FormEntity(form);
    return this.formRepository.save(entity);
  }

  public FormEntity updateForm(@NotNull Long id, @Valid FormConcept params) {
    FormEntity entity = this.getForm(id);

    entity.setForm(params);
    return this.formRepository.save(entity);
  }

  public FormEntity deleteForm(@NotNull Long id) {
    FormEntity entity = this.getForm(id);

    this.formRepository.delete(entity);
    return entity;
  }
}
