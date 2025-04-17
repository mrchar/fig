package net.mrchar.fig.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.datafaker.Faker;
import net.mrchar.fig.authentication.UserEntity;
import net.mrchar.fig.space.SpaceEntity;

public class SpaceEntityGenerator {
  private static final Faker FAKER = new Faker(Locale.CHINA);

  public static SpaceEntity generate(UserEntity owner) {
    return new SpaceEntity(FAKER.company().name(), owner);
  }

  public static List<SpaceEntity> generate(UserEntity owner, Integer count) {
    List<SpaceEntity> entities = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      entities.add(generate(owner));
    }
    return entities;
  }
}
