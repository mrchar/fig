package net.mrchar.fig.authentication;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  @Query("select count(1) > 0 from UserEntity u where u.username = :username")
  Boolean existsByUsername(@NotNull String username);

  @Query("from UserEntity u where u.username = :username")
  Optional<UserEntity> findByUsername(@NotNull String username);

  @Query("from UserEntity u where u.code = :code")
  Optional<UserEntity> findByCode(@NotNull String code);

  @Query("from UserEntity u where u.role = :role")
  List<UserEntity> listAllByRole(Role role);
}
