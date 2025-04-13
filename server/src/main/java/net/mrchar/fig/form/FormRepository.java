package net.mrchar.fig.form;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, Long> {
  @Query("from FormEntity f where f.form.name like :#{'%'+#keyword+'%'}")
  Page<FormEntity> searchByNameLikeKeyword(String keyword, Pageable pageable);
}
