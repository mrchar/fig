package net.mrchar.fig.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mrchar.fig.common.AbstractEntity;
import net.mrchar.fig.common.PreparationFailedException;
import org.apache.commons.codec.binary.Base32;
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

  private static final Base32 base32 = new Base32();

  @Column(name = "code")
  private String code;

  @Setter
  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;

  @Setter
  @NotBlank
  @Column(name = "name")
  private String username;

  @NotBlank
  @JsonIgnore
  @Column(name = "password")
  private String password;

  public void setPassword(String password) {
    this.password = encoder.encode(password);
  }

  public UserEntity(String username, String password) {
    this.code = generateCode(username);
    this.role = Role.USER;
    this.username = username;
    this.password = password;
  }

  @Override
  @Transient
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  private static String generateCode(String username) {
    MessageDigest sha1;
    try {
      sha1 = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      throw new PreparationFailedException("创建失败，请稍候重试。");
    }

    sha1.update(username.getBytes());
    sha1.update(Instant.now().toString().getBytes());

    byte[] hash = sha1.digest();

    return new String(base32.encode(hash));
  }
}
