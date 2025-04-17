package net.mrchar.fig.common;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExists extends AbstractException {
  public ResourceAlreadyExists(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
