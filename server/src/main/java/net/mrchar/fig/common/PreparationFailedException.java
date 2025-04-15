package net.mrchar.fig.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

public class PreparationFailedException extends AbstractException {
  public static final HttpStatusCode HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

  public PreparationFailedException(String detail) {
    super(HTTP_STATUS, detail);
  }

  public PreparationFailedException(HttpStatusCode status, ProblemDetail body, Throwable cause) {
    super(HTTP_STATUS, body, cause);
  }

  public PreparationFailedException(
      HttpStatusCode status,
      ProblemDetail body,
      Throwable cause,
      String messageDetailCode,
      Object[] messageDetailArguments) {
    super(HTTP_STATUS, body, cause, messageDetailCode, messageDetailArguments);
  }
}
