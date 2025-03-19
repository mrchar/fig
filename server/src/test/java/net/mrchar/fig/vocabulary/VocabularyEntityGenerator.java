package net.mrchar.fig.vocabulary;

import java.util.HashMap;
import java.util.Locale;
import net.datafaker.Faker;

public class VocabularyEntityGenerator {
  private static final Faker faker = new Faker(Locale.CHINA);

  public static VocabularyEntity generate() {
    VocabularyConcept concept = new VocabularyConcept(faker.text().text(10), new HashMap<>());
    return new VocabularyEntity(concept);
  }
}
