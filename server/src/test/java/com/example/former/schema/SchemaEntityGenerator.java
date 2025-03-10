package com.example.former.schema;

import java.util.HashMap;
import java.util.Locale;
import net.datafaker.Faker;

public class SchemaEntityGenerator {
  private static final Faker faker = new Faker(Locale.CHINA);

  public static SchemaEntity generate() {
    SchemaConcept schema = new SchemaConcept(faker.text().text(10), new HashMap<>());
    return new SchemaEntity(schema);
  }
}
