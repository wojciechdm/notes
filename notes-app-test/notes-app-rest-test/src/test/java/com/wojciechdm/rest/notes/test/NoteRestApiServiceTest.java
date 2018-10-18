package com.wojciechdm.rest.notes.test;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.polsource.assignment.backend.Note;
import com.polsource.assignment.backend.NoteDto;
import com.polsource.assignment.backend.NoteHistory;

public class NoteRestApiServiceTest {

	private RestTemplate restTemplate=new RestTemplate();
	private String url="http://localhost:8080/notes-webapp/notes";	
	private NoteDto noteDto=new NoteDto();
	
	@Before
	public void cleanDatabase() {
		ResponseEntity<List<Note>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>(){});
		List<Note> listOfNotes = response.getBody();
		for(Note note: listOfNotes) {
			restTemplate.delete(url+"/"+note.getNoteId());
		}
	}
	
	@Test
	public void shouldGiveListOfNotes() {
		List<Note> expectedListOfNotes=new ArrayList<>();
		noteDto.setTitle("title1");		
		noteDto.setContent("Content1");
		Note expectedNote=expectedNote(noteDto);
		expectedListOfNotes.add(expectedNote);
		restTemplate.postForObject(url, noteDto, Note.class);
		noteDto.setTitle("title2");		
		noteDto.setContent("Content2");
		expectedNote=expectedNote(noteDto);
		expectedListOfNotes.add(expectedNote);
		restTemplate.postForObject(url, noteDto, Note.class);
		ResponseEntity<List<Note>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>(){});
		List<Note> listOfNotes = response.getBody();
		assertEquals(listOfNotes, expectedListOfNotes);
	}	
	
	@Test
	public void shouldGiveNote() {
		noteDto.setTitle("title");		
		noteDto.setContent("Content");
		Note expectedNote=expectedNote(noteDto);
		restTemplate.postForObject(url, noteDto, Note.class);
		ResponseEntity<List<Note>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>(){});
		List<Note> listOfNotes = response.getBody();
		expectedNote.setNoteId(listOfNotes.get(0).getNoteId());
		Note responseNote=restTemplate.getForObject(url+"/"+listOfNotes.get(0).getNoteId(), Note.class);
		assertEquals(responseNote, expectedNote);
	}
	
	@Test
	public void shouldNotFoundErrorWhenTryGetNoteNotExists() {		
		try {
			restTemplate.getForObject(url+"/0", Note.class);
		}
		catch(HttpClientErrorException exception){
			assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
		}
	}
	
	@Test
	public void shouldGiveListOfNoteHistory() {
		List<NoteHistory> expectedListOfNoteHistory=new ArrayList<>();
		NoteHistory noteHistory;
		Note responseNote;
		noteDto.setTitle("title");		
		noteDto.setContent("Content");
		responseNote=restTemplate.postForObject(url, noteDto, Note.class);
		ResponseEntity<List<Note>> responseNotes = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>(){});
		List<Note> listOfNotes = responseNotes.getBody();
		responseNote=listOfNotes.get(0);		
		noteHistory=new NoteHistory(responseNote,0L);
		expectedListOfNoteHistory.add(noteHistory);
		noteDto.setTitle("title2");		
		noteDto.setContent("Content2");
		restTemplate.put(url+"/"+responseNote.getNoteId(), noteDto);
		responseNote=restTemplate.getForObject(url+"/"+responseNote.getNoteId(), Note.class);
		noteHistory=new NoteHistory(responseNote,0L);
		expectedListOfNoteHistory.add(noteHistory);
		noteDto.setTitle("title3");		
		noteDto.setContent("Content3");
		restTemplate.put(url+"/"+responseNote.getNoteId(), noteDto);
		ResponseEntity<List<NoteHistory>> responseNoteHistory = restTemplate.exchange(url+"/"+responseNote.getNoteId()+"-history", 
				HttpMethod.GET, null, new ParameterizedTypeReference<List<NoteHistory>>(){});
		List<NoteHistory> listOfNoteHistory = responseNoteHistory.getBody();
		assertEquals(listOfNoteHistory, expectedListOfNoteHistory);
	}
	
	@Test
	public void shouldNotFoundErrorWhenTryGetNoteHistoryNotExists() {		
		try {
			restTemplate.exchange(url+"/0-history", HttpMethod.GET, null, new ParameterizedTypeReference<List<NoteHistory>>(){});			
		}
		catch(HttpClientErrorException exception){
			assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
		}
	}
	
	@Test
	public void shouldSaveNote() {
		noteDto.setTitle("title");		
		noteDto.setContent("Content");
		Note expectedNote=expectedNote(noteDto);
		Note responseNote=restTemplate.postForObject(url, noteDto, Note.class);
		assertEquals(responseNote, expectedNote);
	}
	
	@Test
	public void shouldBadRequestErrorWhenTrySaveNoteWithEmptyTitleOrContent(){
		noteDto=new NoteDto();
		try {
			restTemplate.postForObject(url, noteDto, Note.class);
		}
		catch(HttpClientErrorException exception){
			assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
		}
	}
	
	@Test
	public void shouldEditNote() {
		noteDto.setTitle("title");		
		noteDto.setContent("Content");
		Note responseNote=restTemplate.postForObject(url, noteDto, Note.class);
		ResponseEntity<List<Note>> responseNotes = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>(){});
		List<Note> listOfNotes = responseNotes.getBody();
		responseNote=listOfNotes.get(0);	
		noteDto.setTitle("title2");		
		noteDto.setContent("Content2");
		Note expectedNote=expectedNote(noteDto);
		expectedNote.setVersion(2L);
		restTemplate.put(url+"/"+responseNote.getNoteId(), noteDto);
		responseNote=restTemplate.getForObject(url+"/"+responseNote.getNoteId(), Note.class);
		assertEquals(responseNote, expectedNote);
	}
	
	@Test
	public void shouldBadRequestErrorWhenTryEditNoteWithEmptyTitleOrContent() {	
		noteDto.setTitle("title");		
		noteDto.setContent("Content");
		Note responseNote=restTemplate.postForObject(url, noteDto, Note.class);
		ResponseEntity<List<Note>> responseNotes = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>(){});
		List<Note> listOfNotes = responseNotes.getBody();
		responseNote=listOfNotes.get(0);		
		noteDto=new NoteDto();	
		try {
			restTemplate.put(url+"/"+responseNote.getNoteId(), noteDto);
		}
		catch(HttpClientErrorException exception){
			assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
		}
	}
	
	@Test
	public void shouldDeleteNote() {
		noteDto.setTitle("title");		
		noteDto.setContent("Content");		
		Note responseNote=restTemplate.postForObject(url, noteDto, Note.class);
		ResponseEntity<List<Note>> responseNotes = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>(){});
		List<Note> listOfNotes = responseNotes.getBody();
		responseNote=listOfNotes.get(0);
		restTemplate.delete(url+"/"+responseNote.getNoteId());
		try {
			responseNote=restTemplate.getForObject(url+"/"+responseNote.getNoteId(), Note.class);
		}
		catch(HttpClientErrorException exception){
			assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
		}
	}
	
	@Test
	public void shouldNotFoundErrorWhenTryDeleteNoteNotExists() {
		try {
			restTemplate.delete(url+"/0");
		}
		catch(HttpClientErrorException exception){
			assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
		}
	}
	
	public Note expectedNote(NoteDto noteDto) {
		Note expectedNote=new Note();
		expectedNote.setTitle(noteDto.getTitle());
		expectedNote.setContent(noteDto.getContent());
		expectedNote.setCreated(LocalDate.now());
		expectedNote.setModified(LocalDate.now());
		expectedNote.setVersion(1L);
		return expectedNote;
	}
}
