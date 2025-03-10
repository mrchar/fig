package com.example.former.data;

import com.example.former.form.FormEntity;
import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Locale;
import net.datafaker.Faker;

public class RecordEntityGenerator {
  private static final Faker FAKER = new Faker(Locale.CHINA);

  public static RecordEntity generate(@NotNull FormEntity formEntity) {
    if (formEntity.isNew()) {
      throw new IllegalArgumentException("Form entity is new");
    }

    return new RecordEntity(formEntity, new HashMap<>());
  }
}
