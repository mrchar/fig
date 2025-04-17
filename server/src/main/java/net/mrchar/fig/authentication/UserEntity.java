package net.mrchar.fig.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mrchar.fig.common.AbstractEntity;
import net.mrchar.fig.util.RandomUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "local_user")
@JsonIgnoreProperties({
  "new",
  "password",
  "authorities",
  "enabled",
  "accountNonExpired",
  "accountNonLocked",
  "credentialsNonExpired"
})
public class UserEntity extends AbstractEntity implements UserDetails {
  private static final PasswordEncoder encoder =
      PasswordEncoderFactories.createDelegatingPasswordEncoder();

  @Column(name = "code")
  private String code;

  @Setter
  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;

  @Setter
  @NotBlank
  @Column(name = "username")
  private String username;

  @NotBlank
  @JsonIgnore
  @Column(name = "password")
  private String password;

  public void setPassword(String password) {
    this.password = encoder.encode(password);
  }

  public UserEntity(String username, String password) {
    this.code = RandomUtil.generateCode(username);
    this.role = Role.USER;
    this.username = username;
    this.password = password;
  }

  @Override
  @Transient
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }
}
