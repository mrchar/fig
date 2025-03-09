package com.example.former.form;

import com.example.former.common.ResourceNotExistsException;
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

  public FormEntity addForm(@Valid FormConcept form) {
    FormEntity entity = new FormEntity(form);
    return this.formRepository.save(entity);
  }

  public FormEntity updateForm(@NotNull Long id, @Valid FormConcept params) {
    FormEntity entity =
        this.formRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotExistsException("Form does not exist"));

    entity.setForm(params);
    return this.formRepository.save(entity);
  }

  public FormEntity deleteForm(@NotNull Long id) {
    FormEntity entity =
        this.formRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotExistsException("Form does not exist"));

    this.formRepository.delete(entity);
    return entity;
  }
}
