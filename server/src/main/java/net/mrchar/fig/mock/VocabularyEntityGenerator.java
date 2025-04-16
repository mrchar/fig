package net.mrchar.fig.mock;

import java.util.*;
import net.datafaker.Faker;
import net.mrchar.fig.vocabulary.VocabularyConcept;
import net.mrchar.fig.vocabulary.VocabularyEntity;

public class VocabularyEntityGenerator {
  private static final Faker faker = new Faker(Locale.CHINA);

  public static VocabularyEntity generate() {
    String name = faker.letterify("vocabulary??????????");
    String description = faker.letterify("vocabulary description ??????????");
    Map<String, Object> jsonSchema =
        Map.of(
            "$schema",
            "https://json-schema.org/draft-07/schema#",
            "type",
            "string",
            "title",
            "name",
            "description",
            "This schema describes a student name.");
    VocabularyConcept concept = new VocabularyConcept(name, description, jsonSchema);
    return new VocabularyEntity(concept);
  }

  public static List<VocabularyEntity> generate(Integer count) {
    List<VocabularyEntity> entities = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      entities.add(generate());
    }

    return entities;
  }
}
