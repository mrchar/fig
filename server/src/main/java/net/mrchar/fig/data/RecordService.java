package net.mrchar.fig.data;

import net.mrchar.fig.common.ResourceNotExistsException;
import net.mrchar.fig.form.FormEntity;
import net.mrchar.fig.form.FormRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class RecordService {
  private final FormRepository formRepository;
  private final RecordRepository recordRepository;

  public RecordEntity getRecord(@NotNull Long id) {
    return this.recordRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotExistsException("Record not found"));
  }

  public RecordEntity addRecord(@NotNull FormEntity form, @NotNull Object content) {
    if (form.isNew()) {
      throw new ResourceNotExistsException("Form not created");
    }

    return this.recordRepository.save(new RecordEntity(form, content));
  }

  public RecordEntity updateRecord(@NotNull Long id, @NotNull Object params) {
    RecordEntity entity = this.getRecord(id);

    entity.setContent(params);
    return this.recordRepository.save(entity);
  }

  public RecordEntity deleteRecord(@NotNull Long id) {
    RecordEntity entity = this.getRecord(id);

    this.recordRepository.delete(entity);
    return entity;
  }
}
