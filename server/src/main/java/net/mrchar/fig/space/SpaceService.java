package net.mrchar.fig.space;

import lombok.RequiredArgsConstructor;
import net.mrchar.fig.authentication.UserEntity;
import net.mrchar.fig.authentication.UserRepository;
import net.mrchar.fig.common.PreparationFailedException;
import net.mrchar.fig.common.ResourceAlreadyExists;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceService {
  private final UserRepository userRepository;
  private final SpaceRepository spaceRepository;

  public Page<SpaceEntity> listSpaces(Pageable pageable) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    UserEntity userEntity =
        this.userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new PreparationFailedException("User not found by username: " + username));

    return this.spaceRepository.findAllByOwner(userEntity, pageable);
  }

  public SpaceEntity addSpace(String name) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity userEntity =
        this.userRepository
            .findByUsername(username)
            .orElseThrow(() -> new PreparationFailedException("无法创建，请稍候重试。"));

    this.spaceRepository
        .findByOwnerAndName(userEntity, name)
        .ifPresent(
            (entity) -> {
              throw new ResourceAlreadyExists("Space with name " + name + " already exists");
            });

    SpaceEntity entity = new SpaceEntity(name, userEntity);
    return this.spaceRepository.save(entity);
  }

  public SpaceEntity getSpace(String code) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity userEntity =
        this.userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new PreparationFailedException("User not found by username: " + username));

    return this.spaceRepository
        .findByOwnerAndCode(userEntity, code)
        .orElseThrow(
            () -> new ResourceNotExistsException("Space with code " + code + " does not exist"));
  }

  public SpaceEntity updateSpace(String code, String name) {
    SpaceEntity entity = this.getSpace(code);
    entity.setName(name);
    return this.spaceRepository.save(entity);
  }

  public SpaceEntity deleteSpace(String code) {
    SpaceEntity entity = this.getSpace(code);
    this.spaceRepository.delete(entity);
    return entity;
  }
}
