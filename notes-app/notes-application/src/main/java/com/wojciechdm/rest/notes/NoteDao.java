package com.wojciechdm.rest.notes;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDao extends CrudRepository<Note,Long>{

	public Optional<Note> findByNoteId(Long noteId);
	public List<Note> findAll();
	@SuppressWarnings("unchecked")
	public Note save(Note note);
	@Transactional
	public void deleteByNoteId(Long noteId);
	
}
