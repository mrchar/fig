package net.mrchar.fig.common;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ConstraintViolationException.class)
  public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle("Constraint Violation Exception");
    problemDetail.setDetail(e.getMessage());
    return problemDetail;
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    problemDetail.setTitle("Data Integrity Violation Exception");
    problemDetail.setDetail(e.getMessage());
    return problemDetail;
  }

  @ExceptionHandler(ResourceNotExistsException.class)
  public ProblemDetail handleResourceNotExistsException(ResourceNotExistsException e) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    problemDetail.setTitle("Resource NotExists Exception");
    problemDetail.setDetail(e.getMessage());
    return problemDetail;
  }
}
