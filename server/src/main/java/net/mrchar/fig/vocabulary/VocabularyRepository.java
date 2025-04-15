package net.mrchar.fig.vocabulary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabularyRepository extends JpaRepository<VocabularyEntity, Long> {
  @Query("from VocabularyEntity v where v.vocabulary.name like :#{'%'+#keyword+'%'}")
  Page<VocabularyEntity> searchByNameContainsKeyword(String keyword, Pageable pageable);
}
