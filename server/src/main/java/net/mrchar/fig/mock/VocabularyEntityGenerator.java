package net.mrchar.fig.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.datafaker.Faker;
import net.mrchar.fig.vocabulary.VocabularyConcept;
import net.mrchar.fig.vocabulary.VocabularyEntity;

public class VocabularyEntityGenerator {
  private static final Faker faker = new Faker(Locale.CHINA);

  public static VocabularyEntity generate() {
    VocabularyConcept concept = new VocabularyConcept(faker.text().text(10), new HashMap<>());
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
