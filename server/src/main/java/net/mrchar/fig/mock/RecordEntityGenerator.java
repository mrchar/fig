package net.mrchar.fig.mock;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.datafaker.Faker;
import net.mrchar.fig.data.RecordEntity;
import net.mrchar.fig.form.FormEntity;

public class RecordEntityGenerator {
  private static final Faker FAKER = new Faker(Locale.CHINA);

  public static RecordEntity generate(@NotNull FormEntity formEntity) {
    if (formEntity.isNew()) {
      throw new IllegalArgumentException("Form entity is new");
    }

    return new RecordEntity(formEntity, new HashMap<>());
  }

  public static List<RecordEntity> generate(@NotNull FormEntity formEntity, Integer count) {
    List<RecordEntity> entities = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      entities.add(generate(formEntity));
    }
    return entities;
  }
}
