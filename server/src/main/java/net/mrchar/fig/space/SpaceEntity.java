package net.mrchar.fig.space;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mrchar.fig.authentication.UserEntity;
import net.mrchar.fig.common.AbstractEntity;
import net.mrchar.fig.util.RandomUtil;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "space")
public class SpaceEntity extends AbstractEntity {
  @Column(name = "code")
  private String code;

  @Setter
  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private UserEntity owner;

  public SpaceEntity(String name, UserEntity owner) {
    this.code = RandomUtil.generateCode(name);
    this.name = name;
    this.owner = owner;
  }
}
