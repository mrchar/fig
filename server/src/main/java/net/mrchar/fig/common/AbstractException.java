package net.mrchar.fig.common;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public abstract class AbstractException extends ErrorResponseException {
  public AbstractException(HttpStatusCode status, String detail) {
    super(status);
    setDetail(detail);
  }

  public AbstractException(HttpStatusCode status, ProblemDetail body, Throwable cause) {
    super(status, body, cause);
  }

  public AbstractException(
      HttpStatusCode status,
      ProblemDetail body,
      Throwable cause,
      String messageDetailCode,
      Object[] messageDetailArguments) {
    super(status, body, cause, messageDetailCode, messageDetailArguments);
  }
}
