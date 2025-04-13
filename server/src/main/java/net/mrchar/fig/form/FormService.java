package net.mrchar.fig.form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.common.ResourceNotExistsException;
import net.mrchar.fig.struct.StructEntity;
import net.mrchar.fig.struct.StructRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class FormService {
  private final StructRepository structRepository;
  private final FormRepository formRepository;

  public FormEntity getForm(@NotNull Long id) {
    return this.formRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotExistsException("Form does not exist"));
  }

  public FormEntity addForm(@NotNull Long structId, @Valid FormConcept form) {
    StructEntity structEntity =
        this.structRepository
            .findById(structId)
            .orElseThrow(() -> new ResourceNotExistsException("Struct does not exist"));

    form.setStruct(structEntity);
    FormEntity entity = new FormEntity(form);
    return this.formRepository.save(entity);
  }

  public FormEntity updateForm(
      @NotNull Long id, @NotNull Long structId, @Valid FormConcept params) {
    FormEntity entity = this.getForm(id);

    FormConcept form = entity.getForm();
    form.setName(params.getName());
    form.setDescription(params.getDescription());
    if (!form.getStruct().getId().equals(structId)) {
      StructEntity structEntity =
          this.structRepository
              .findById(structId)
              .orElseThrow(() -> new ResourceNotExistsException("Struct does not exist"));
      form.setStruct(structEntity);
    }
    form.setUiSchema(params.getUiSchema());

    return this.formRepository.save(entity);
  }

  public FormEntity deleteForm(@NotNull Long id) {
    FormEntity entity = this.getForm(id);

    this.formRepository.delete(entity);
    return entity;
  }
}
