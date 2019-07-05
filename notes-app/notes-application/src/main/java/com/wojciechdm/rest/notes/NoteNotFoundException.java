package com.wojciechdm.rest.notes;

public class NoteNotFoundException extends RuntimeException {

  NoteNotFoundException(String message) {
    super(message);
  }
}
