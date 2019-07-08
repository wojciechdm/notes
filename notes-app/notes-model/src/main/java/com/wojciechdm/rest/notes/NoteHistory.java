package com.wojciechdm.rest.notes;

import lombok.*;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "notes_history")
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class NoteHistory {

  @Id
  @Column(name = "note_history_id", nullable = false)
  private Long noteHistoryId;

  @Column(name = "note_id", nullable = false)
  private Long noteId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private LocalDate created;

  @Column(nullable = false)
  private LocalDate modified;

  @Column(nullable = false)
  private Long version;

  public NoteHistory(Note note, long noteHistoryId) {
    this.noteHistoryId = noteHistoryId;
    this.noteId = note.getNoteId();
    this.title = note.getTitle();
    this.content = note.getContent();
    this.created = note.getCreated();
    this.modified = note.getModified();
    this.version = note.getVersion();
  }
}
