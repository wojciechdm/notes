package com.wojciechdm.rest.notes;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class NoteService {

  private NoteDao noteDao;
  private NoteHistoryService noteHistoryService;
  private NoteConverter noteConverter;

  public NoteDisplayDto fetch(long id) {
    return noteDao
        .findById(id)
        .map(noteConverter::toDto)
        .orElseThrow(() -> new NoteNotFoundException("Note id " + id + " not found"));
  }

  public boolean isNoteExists(long id) {
    return noteDao.findById(id).isPresent();
  }

  public List<NoteDisplayDto> fetchAll() {
    return noteDao.findAll().stream().map(noteConverter::toDto).collect(Collectors.toList());
  }

  public NoteDisplayDto save(NoteSaveDto note) {
    return noteConverter.toDto(noteDao.save(noteConverter.toEntity(note)));
  }

  public NoteDisplayDto update(long id, NoteSaveDto note) {

    Note previous =
        noteDao
            .findById(id)
            .orElseThrow(() -> new NoteNotFoundException("Note id " + id + " not found"));

    noteHistoryService.add(previous);

    return noteConverter.toDto(noteDao.save(noteConverter.toEntity(note, previous)));
  }

  public void delete(long id) {

    Note previous =
        noteDao
            .findById(id)
            .orElseThrow(() -> new NoteNotFoundException("Note id " + id + " not found"));

    noteHistoryService.add(previous);

    noteDao.deleteById(id);
  }
}
