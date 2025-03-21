package net.mrchar.fig.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@JsonIgnoreProperties("new")
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Persistable<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @CreatedDate
  @Column(name = "created_at")
  private Instant createdAt;

  @Setter
  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  @Override
  public boolean isNew() {
    return null == getId();
  }
}
