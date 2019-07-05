package com.wojciechdm.rest.notes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface NoteDao extends CrudRepository<Note, Long> {

  List<Note> findAll();
}
