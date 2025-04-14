package net.mrchar.fig.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.datafaker.Faker;
import net.mrchar.fig.form.FormConcept;
import net.mrchar.fig.form.FormEntity;
import net.mrchar.fig.struct.StructEntity;

public class FormEntityGenerator {
  private static final Faker FAKER = new Faker();

  public static FormEntity generate(StructEntity struct) {
    String name = FAKER.letterify("form??????????");
    String description = FAKER.text().text(20);
    Map<String, Object> uiSchema =
        Map.of(
            "type",
            "HorizontalLayout",
            "elements",
            List.of(
                Map.of("type", "Control", "scope", "#/properties/name"),
                Map.of("type", "Control", "scope", "#/properties/age")));
    FormConcept concept = new FormConcept(name, description, struct, uiSchema);
    return new FormEntity(concept);
  }

  public static List<FormEntity> generate(StructEntity struct, Integer count) {
    List<FormEntity> entities = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      entities.add(generate(struct));
    }
    return entities;
  }
}
