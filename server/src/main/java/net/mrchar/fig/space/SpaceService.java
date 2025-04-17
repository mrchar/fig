package net.mrchar.fig.space;

import lombok.RequiredArgsConstructor;
import net.mrchar.fig.authentication.UserEntity;
import net.mrchar.fig.authentication.UserRepository;
import net.mrchar.fig.common.PreparationFailedException;
import net.mrchar.fig.common.ResourceAlreadyExists;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceService {
  private final UserRepository userRepository;
  private final SpaceRepository spaceRepository;

  public SpaceEntity addSpace(String name) {
    this.spaceRepository
        .findByNameForUser(name)
        .ifPresent(
            (entity) -> {
              throw new ResourceAlreadyExists("Space with name " + name + " already exists");
            });

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity userEntity =
        this.userRepository
            .findByUsername(username)
            .orElseThrow(() -> new PreparationFailedException("无法创建，请稍候重试。"));

    SpaceEntity entity = new SpaceEntity(name, userEntity);
    return this.spaceRepository.save(entity);
  }

  public SpaceEntity getSpace(String code) {
    return this.spaceRepository
        .findByCodeForUser(code)
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
