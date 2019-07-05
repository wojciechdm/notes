package com.wojciechdm.rest.notes;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/notes")
@AllArgsConstructor
public class NoteRestController {

  private NoteService noteService;
  private NoteHistoryService noteHistoryService;

  @GetMapping()
  public List<NoteDisplayDto> fetchAll() {

    return noteService.fetchAll();
  }

  @GetMapping(value = "/{id}")
  public NoteDisplayDto fetch(@PathVariable("id") long id) {

    return noteService.fetch(id);
  }

  @GetMapping(value = "/{id}/history")
  public List<NoteHistoryDto> fetchHistory(@PathVariable("id") long id) {

    return noteHistoryService.fetch(id);
  }

  @ResponseStatus(CREATED)
  @PostMapping()
  public NoteDisplayDto save(@RequestBody NoteSaveDto noteSaveDto) {

    return noteService.save(noteSaveDto);
  }

  @PutMapping(value = "/{id}")
  public NoteDisplayDto update(@PathVariable("id") long id, @RequestBody NoteSaveDto noteSaveDto) {

    return noteService.update(id, noteSaveDto);
  }

  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable("id") long id) {

    noteService.delete(id);
  }
}
