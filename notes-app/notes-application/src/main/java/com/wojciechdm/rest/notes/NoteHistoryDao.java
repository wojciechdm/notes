package com.wojciechdm.rest.notes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface NoteHistoryDao extends CrudRepository<NoteHistory, Long> {

	List<NoteHistory> findAllByNoteId(long noteId);
}
