package net.mrchar.fig.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public enum Role {
  ADMIN("admin"),
  USER("user");

  @JsonValue private final String name;

  Role(String name) {
    this.name = name;
  }

  @JsonCreator
  public static Role ofName(@NotNull String name) {
    for (Role value : Role.values()) {
      if (value.getName().equals(name)) {
        return value;
      }
    }

    return null;
  }
}
