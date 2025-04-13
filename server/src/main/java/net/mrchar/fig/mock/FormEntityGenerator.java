package net.mrchar.fig.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.datafaker.Faker;
import net.mrchar.fig.form.FormConcept;
import net.mrchar.fig.form.FormEntity;
import net.mrchar.fig.struct.StructEntity;

public class FormEntityGenerator {
  private static final Faker FAKER = new Faker();

  public static FormEntity generate(StructEntity struct) {
    FormConcept concept =
        new FormConcept(FAKER.text().text(10), FAKER.text().text(20), struct, new HashMap<>());
    return new FormEntity(concept);
  }

  public static List<FormEntity> generate(StructEntity struct, Integer count) {
    List<FormEntity> entities = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      entities.add(generate(struct));
    }
    return entities;
  }
}
