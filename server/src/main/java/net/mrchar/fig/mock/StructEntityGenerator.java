package net.mrchar.fig.mock;

import java.util.*;
import net.datafaker.Faker;
import net.mrchar.fig.struct.StructConcept;
import net.mrchar.fig.struct.StructEntity;

public class StructEntityGenerator {
  private static final Faker faker = new Faker(Locale.CHINA);

  public static StructEntity generate() {
    String name = faker.letterify("struct??????????");
    Map<String, Object> jsonSchema =
        Map.of(
            "$schema", "http://json-schema.org/draft-07/schema#",
            "type", "object",
            "title", "student",
            "description", "This schema describes a student.",
            "properties",
                Map.of(
                    "name", Map.of("type", "string", "title", "name"),
                    "age", Map.of("type", "integer", "title", "age")));
    StructConcept concept = new StructConcept(name, jsonSchema);
    return new StructEntity(concept);
  }

  public static List<StructEntity> generate(Integer count) {
    List<StructEntity> entities = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      entities.add(generate());
    }
    return entities;
  }
}
