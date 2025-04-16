package net.mrchar.fig.function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionRepository extends JpaRepository<FunctionEntity, Long> {
    @Query("""
            from FunctionEntity f
                        where f.function.name like :#{'%'+#keyword+'%'}""")
    Page<FunctionEntity> searchByCodeOrNameLikeKeyword(String keyword, Pageable pageable);
}
