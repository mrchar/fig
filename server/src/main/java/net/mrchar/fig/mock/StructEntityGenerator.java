package net.mrchar.fig.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.datafaker.Faker;
import net.mrchar.fig.struct.StructConcept;
import net.mrchar.fig.struct.StructEntity;

public class StructEntityGenerator {
  private static final Faker faker = new Faker(Locale.CHINA);

  public static StructEntity generate() {
    StructConcept schema = new StructConcept(faker.text().text(10), new HashMap<>());
    return new StructEntity(schema);
  }

  public static List<StructEntity> generate(Integer count) {
    List<StructEntity> entities = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      entities.add(generate());
    }
    return entities;
  }
}
