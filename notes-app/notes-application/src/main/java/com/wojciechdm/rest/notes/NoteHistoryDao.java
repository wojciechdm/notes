package com.wojciechdm.rest.notes;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface NoteHistoryDao extends CrudRepository<NoteHistory, Long> {

	public List<NoteHistory> findAllByNoteId(Long noteId);
	@SuppressWarnings("unchecked")
	public NoteHistory save(NoteHistory noteHistory);
	
}
