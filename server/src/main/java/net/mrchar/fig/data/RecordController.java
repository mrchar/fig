package net.mrchar.fig.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.mrchar.fig.common.ResourceNotExistsException;
import net.mrchar.fig.form.FormEntity;
import net.mrchar.fig.form.FormService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
public class RecordController {
  private final RecordRepository recordRepository;
  private final FormService formService;
  private final RecordService recordService;

  @GetMapping
  public Page<RecordEntity> listRecords(Pageable pageable) {
    return this.recordRepository.findAll(pageable);
  }

  @GetMapping("{id}")
  public RecordEntity getRecord(@PathVariable Long id) {
    return this.recordRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotExistsException("Record does not exists"));
  }

  @GetMapping(params = {"formId"})
  public Page<RecordEntity> listRecords(@RequestParam Long formId, Pageable pageable) {
    return this.recordRepository.findAllByFormId(formId, pageable);
  }

  @Getter
  @Setter
  public static class AddRecordParams {
    private Long formId;
    private Object content;
  }

  @PostMapping
  public RecordEntity addRecord(@RequestBody AddRecordParams params) {
    FormEntity formEntity = this.formService.getForm(params.getFormId());
    return this.recordService.addRecord(formEntity, params.getContent());
  }

  @PutMapping("{id}")
  public RecordEntity updateRecord(@PathVariable Long id, @RequestBody Object params) {
    return this.recordService.updateRecord(id, params);
  }

  @DeleteMapping("{id}")
  public RecordEntity deleteRecord(@PathVariable Long id) {
    return this.recordService.deleteRecord(id);
  }
}
