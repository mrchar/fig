package net.mrchar.fig.authentication;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import java.lang.module.ResolutionException;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.mrchar.fig.common.ResourceNotExistsException;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {
  private final SecurityProperties securityProperties;
  private final UserRepository userRepository;

  @PostConstruct
  public void addAdminUser() {
    List<UserEntity> admins = this.userRepository.listAllByRole(Role.ADMIN);
    if (!CollectionUtils.isEmpty(admins)) {
      return;
    }

    SecurityProperties.User user = this.securityProperties.getUser();
    if (!StringUtils.hasText(user.getName()) && !StringUtils.hasText(user.getPassword())) {
      throw new RuntimeException("没有配置管理员账号和密码");
    }

    UserEntity entity = new UserEntity(user.getName(), user.getPassword());
    this.userRepository.save(entity);
  }

  public UserEntity getUserByCode(@NotNull String code) {
    return this.userRepository
        .findByCode(code)
        .orElseThrow(() -> new ResourceNotExistsException("User not found"));
  }

  public UserEntity addUser(@NotNull String username) {
    Boolean exits = this.userRepository.existsByUsername(username);
    if (exits) {
      throw new ResolutionException("Username not available.");
    }

    String password = PasswordUtil.generatePassword();
    UserEntity entity = new UserEntity(username, password);
    return this.userRepository.save(entity);
  }

  @Getter
  @Setter
  public static class UpdateUserParams {
    private Role role;
    private String username;
    private String password;
  }

  public UserEntity updateUserName(@NotNull String code, @NotNull UpdateUserParams params) {
    UserEntity entity = this.getUserByCode(code);

    if (params.getRole() != null) {
      entity.setRole(params.getRole());
    }
    if (params.getUsername() != null) {
      entity.setUsername(params.getUsername());
    }
    if (params.getPassword() != null) {
      entity.setPassword(params.getPassword());
    }

    return this.userRepository.save(entity);
  }

  public UserEntity updatePassword(@NotNull String code, @NotNull String password) {
    UserEntity entity = this.getUserByCode(code);
    entity.setPassword(PasswordUtil.generatePassword());
    return this.userRepository.save(entity);
  }

  public UserEntity deleteUser(@NotNull String code) {
    UserEntity entity = this.getUserByCode(code);
    this.userRepository.delete(entity);
    return entity;
  }
}
