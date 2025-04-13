package net.mrchar.fig.struct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StructRepository extends JpaRepository<StructEntity, Long> {
    @Query("from StructEntity s where s.struct.name like :#{'%'+#keyword+'%'}")
    Page<StructEntity> searchByNameContainsKeyword(String keyword, Pageable pageable);
}
