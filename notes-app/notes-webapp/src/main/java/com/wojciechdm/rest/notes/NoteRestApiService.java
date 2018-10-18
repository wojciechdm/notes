package com.wojciechdm.rest.notes;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteRestApiService {

	@Autowired
	private NoteService noteService;
	@Autowired
	private NoteHistoryService noteHistoryService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Note>> getListOfNotes(){
		List<Note> listOfNotes=noteService.getListOfNotes();
		return new ResponseEntity<List<Note>>(listOfNotes, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{noteId}", method=RequestMethod.GET)
	public ResponseEntity<Note> getNote(@PathVariable("noteId") Long noteId){
		if(noteService.isNoteExists(noteId)) {
			Note note=noteService.getNote(noteId);
			return new ResponseEntity<Note>(note, new HttpHeaders(), HttpStatus.OK);
		}
		return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{noteId}-history", method=RequestMethod.GET)
	public ResponseEntity<List<NoteHistory>> getNoteHistory(@PathVariable("noteId") Long noteId){
		List<NoteHistory> listOfNoteHistory=noteHistoryService.getNoteHistory(noteId);
		if(listOfNoteHistory.isEmpty()) {
			return new ResponseEntity<List<NoteHistory>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<NoteHistory>>(listOfNoteHistory, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Note> addNote(@RequestBody @Valid NoteDto noteDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<Note>(HttpStatus.BAD_REQUEST);
		}
		Note note=new Note();
		note.setNoteId(0L);
		note.setTitle(noteDto.getTitle());
		note.setContent(noteDto.getContent());
		note.setCreated(LocalDate.now());
		note.setModified(LocalDate.now());
		note.setVersion(1L);
		note=noteService.addNote(note);
		return new ResponseEntity<Note>(note, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{noteId}", method=RequestMethod.PUT)
	public ResponseEntity<Note> editNote(@PathVariable("noteId") Long noteId, @RequestBody @Valid NoteDto noteDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<Note>(HttpStatus.BAD_REQUEST);
		}
		Note note=noteService.getNote(noteId);
		NoteHistory noteHistory=new NoteHistory(note, 0L);
		note.setTitle(noteDto.getTitle());
		note.setContent(noteDto.getContent());
		note.setModified(LocalDate.now());
		note.setVersion(note.getVersion()+1);
		noteHistoryService.addNoteHistory(noteHistory);
		note=noteService.addNote(note);
		return new ResponseEntity<Note>(note, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{noteId}", method=RequestMethod.DELETE)
	public ResponseEntity<Note> deleteNote(@PathVariable("noteId") Long noteId){
		if(noteService.isNoteExists(noteId)) {
			Note note=noteService.getNote(noteId);
			NoteHistory noteHistory=new NoteHistory(note, 0L);
			noteHistoryService.addNoteHistory(noteHistory);
			noteService.deleteNote(noteId);
			return new ResponseEntity<Note>(HttpStatus.OK);
		}
		return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
	}
	
}
