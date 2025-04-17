package net.mrchar.fig.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.datafaker.Faker;
import net.mrchar.fig.authentication.PasswordUtil;
import net.mrchar.fig.authentication.UserEntity;

public class UserEntityGenerator {
  private static final Faker FAKER = new Faker(Locale.CHINA);

  public static UserEntity generate() {
    return new UserEntity(FAKER.name().fullName(), PasswordUtil.generatePassword());
  }

  public static List<UserEntity> generate(Integer count) {
    List<UserEntity> entities = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      entities.add(generate());
    }
    return entities;
  }
}
