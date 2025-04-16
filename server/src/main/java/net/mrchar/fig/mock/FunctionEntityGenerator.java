package net.mrchar.fig.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.datafaker.Faker;
import net.mrchar.fig.function.FunctionConcept;
import net.mrchar.fig.function.FunctionEntity;

public class FunctionEntityGenerator {
  private static final Faker FAKER = new Faker(Locale.CHINA);

  public static FunctionEntity generate() {
    String name = FAKER.letterify("function??????????");
    String description = FAKER.letterify("description????");
    String content = FAKER.text().text(100);
    FunctionConcept concept = new FunctionConcept(name, description, content);
    return new FunctionEntity(concept);
  }

  public static List<FunctionEntity> generate(Integer count) {
    List<FunctionEntity> entities = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      entities.add(generate());
    }
    return entities;
  }
}
