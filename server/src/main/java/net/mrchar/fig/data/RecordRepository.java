package net.mrchar.fig.data;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Validated
@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {
  Page<RecordEntity> findAllByFormId(@NotNull Long formId, Pageable pageable);
}
