package com.wojciechdm.rest.notes;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

	@Autowired
	private NoteDao noteDao;
	
	public Note getNote(Long noteId) {
		return noteDao.findByNoteId(noteId).get();
	}
	
	public boolean isNoteExists(Long noteId) {
		return noteDao.findByNoteId(noteId).isPresent();
	}
	
	public List<Note> getListOfNotes(){
		return noteDao.findAll();
	}
	
	public Note addNote(Note note) {
		return noteDao.save(note);
	}
	
	public void deleteNote(Long noteId) {
		noteDao.deleteByNoteId(noteId);
	}
	
}
