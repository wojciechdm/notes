package com.wojciechdm.rest.notes;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteHistoryService {

	@Autowired
	private NoteHistoryDao noteHistoryDao;
	
	public List<NoteHistory> getNoteHistory(Long noteId){
		return noteHistoryDao.findAllByNoteId(noteId);
	}
	
	public NoteHistory addNoteHistory(NoteHistory noteHistory) {
		return noteHistoryDao.save(noteHistory);
	}
	
}
