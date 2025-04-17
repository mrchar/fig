package net.mrchar.fig;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.authentication.Role;
import net.mrchar.fig.authentication.UserEntity;
import net.mrchar.fig.authentication.UserRepository;
import net.mrchar.fig.common.ResourceNotExistsException;
import net.mrchar.fig.space.SpaceEntity;
import net.mrchar.fig.space.SpaceRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class FigApplication {
  private final SecurityProperties securityProperties;
  private final UserRepository userRepository;
  private final SpaceRepository spaceRepository;

  public static void main(String[] args) {
    SpringApplication.run(FigApplication.class, args);
  }

  @PostConstruct
  public void init() {
    this.addAdminUser();
    this.addDefaultSpace();
  }

  private void addAdminUser() {
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

  @PostConstruct
  private void addDefaultSpace() {
    long count = this.spaceRepository.count();
    if (count != 0) {
      return;
    }

    if (!StringUtils.hasText(securityProperties.getUser().getName())) {
      throw new ResourceNotExistsException("Admin user is required");
    }

    UserEntity admin =
        this.userRepository
            .findByUsername(securityProperties.getUser().getName())
            .orElseThrow(() -> new ResourceNotExistsException("Admin user not found"));

    this.spaceRepository.save(new SpaceEntity("DEFAULT", admin));
  }
}
