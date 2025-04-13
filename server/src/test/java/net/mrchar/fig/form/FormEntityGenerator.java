package net.mrchar.fig.form;

import java.util.HashMap;
import net.datafaker.Faker;
import net.mrchar.fig.struct.StructEntity;

public class FormEntityGenerator {
  private static final Faker FAKER = new Faker();

  public static FormEntity generate(StructEntity struct) {
    FormConcept concept =
        new FormConcept(FAKER.text().text(10), FAKER.text().text(20), struct, new HashMap<>());
    return new FormEntity(concept);
  }
}
