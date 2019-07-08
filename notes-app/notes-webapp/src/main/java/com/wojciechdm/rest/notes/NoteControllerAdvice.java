package com.wojciechdm.rest.notes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class NoteControllerAdvice {

  @ExceptionHandler(NoteNotFoundException.class)
  public ResponseEntity<String> handle(NoteNotFoundException exception) {
    return error(NOT_FOUND, exception);
  }

  private ResponseEntity<String> error(HttpStatus httpStatus, Exception exception) {
    log.error("Exception: ", exception);

    return ResponseEntity.status(httpStatus).body(exception.getMessage());
  }
}
