package net.mrchar.fig.struct;

import java.util.HashMap;
import java.util.Locale;
import net.datafaker.Faker;

public class StructEntityGenerator {
  private static final Faker faker = new Faker(Locale.CHINA);

  public static StructEntity generate() {
    StructConcept schema = new StructConcept(faker.text().text(10), new HashMap<>());
    return new StructEntity(schema);
  }
}
