package com.wojciechdm.rest.notes;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteHistoryService {

  private NoteHistoryDao noteHistoryDao;
  private NoteConverter noteConverter;

  public List<NoteHistoryDto> fetch(long noteId) {

    return noteHistoryDao.findAllByNoteId(noteId).stream()
        .map(noteConverter::toDto)
        .collect(Collectors.toList());
  }

  void add(Note note) {

   noteHistoryDao.save(noteConverter.toEntity(note));
  }
}
