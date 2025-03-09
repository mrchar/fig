package com.example.former.form;

import java.util.HashMap;
import net.datafaker.Faker;

public class FormEntityGenerator {
  private static final Faker FAKER = new Faker();

  public static final FormEntity generate() {
    FormConcept concept =
        new FormConcept(
            FAKER.text().text(10), FAKER.text().text(20), new HashMap<>(), new HashMap<>());
    FormEntity entity = new FormEntity(concept);
    return entity;
  }
}
