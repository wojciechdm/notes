package com.wojciechdm.rest.notes;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class NoteConverter {

  Note toEntity(NoteSaveDto note) {
    return new Note(0L, note.getTitle(), note.getContent(), LocalDate.now(), LocalDate.now(), 1L);
  }

  Note toEntity(NoteSaveDto note, Note previous) {
    return new Note(
        previous.getNoteId(),
        note.getTitle(),
        note.getContent(),
        previous.getCreated(),
        LocalDate.now(),
        previous.getVersion() + 1);
  }

  NoteHistory toEntity(Note note) {
    return new NoteHistory(
        0L,
        note.getNoteId(),
        note.getTitle(),
        note.getContent(),
        note.getCreated(),
        note.getModified(),
        note.getVersion());
  }

  NoteDisplayDto toDto(Note note) {
    return new NoteDisplayDto(
        note.getNoteId(),
        note.getTitle(),
        note.getContent(),
        note.getCreated(),
        note.getModified(),
        note.getVersion());
  }

  NoteHistoryDto toDto(NoteHistory note) {
    return new NoteHistoryDto(
        note.getNoteHistoryId(),
        note.getNoteId(),
        note.getTitle(),
        note.getContent(),
        note.getCreated(),
        note.getModified(),
        note.getVersion());
  }
}
