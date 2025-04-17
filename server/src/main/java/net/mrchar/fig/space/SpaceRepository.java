package net.mrchar.fig.space;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<SpaceEntity, Long> {
  @Query(
      """
            from SpaceEntity s
                        where s.name = :name
                        and s.owner.username = ?#{principal.username}""")
  Optional<SpaceEntity> findByNameForUser(String name);

  @Query(
      """
              from SpaceEntity s
                            where s.code = :code
                            and s.owner.username = ?#{principal.username}""")
  Optional<SpaceEntity> findByCodeForUser(String code);
}
