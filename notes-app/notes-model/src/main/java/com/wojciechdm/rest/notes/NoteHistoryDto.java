package com.wojciechdm.rest.notes;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class NoteHistoryDto {

  private Long noteHistoryId;
  private Long noteId;
  private String title;
  private String content;
  private LocalDate created;
  private LocalDate modified;
  private Long version;
}
