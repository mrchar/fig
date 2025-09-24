package net.mrchar.fig.space;

import java.util.Optional;
import net.mrchar.fig.authentication.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<SpaceEntity, Long> {
  @Query(
      """
              from SpaceEntity s
                          where s.owner = :owner""")
  Page<SpaceEntity> findAllByOwner(UserEntity owner, Pageable pageable);

  @Query(
      """
            from SpaceEntity s
                        where s.name = :name
                        and s.owner = :owner""")
  Optional<SpaceEntity> findByOwnerAndName(UserEntity owner, String name);

  @Query(
      """
              from SpaceEntity s
                            where s.code = :code
                            and s.owner = :owner""")
  Optional<SpaceEntity> findByOwnerAndCode(UserEntity owner, String code);
}
