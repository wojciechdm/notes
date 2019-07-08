package com.wojciechdm.rest.notes

import static org.hamcrest.core.Is.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.*

import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Stepwise
class NoteRestControllerTest extends Specification {

  @Autowired
  private MockMvc mockMvc
  @Autowired
  private ObjectMapper objectMapper

  def "should save note"() {

    given:

    def expectedNoteSaved = new NoteSaveDto("test title", "test content")

    when:

    def actualNoteSaved =
        this.mockMvc.perform(
            post("/notes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(expectedNoteSaved)))
    then:

    actualNoteSaved
        .andExpect(status().isCreated())
        .andExpect(jsonPath("title", is(expectedNoteSaved.getTitle())))
        .andExpect(jsonPath("content", is(expectedNoteSaved.getContent())))
        .andExpect(jsonPath("created", is(LocalDate.now().toString())))
        .andExpect(jsonPath("modified", is(LocalDate.now().toString())))
        .andExpect(jsonPath("version", is(1)))
  }

  def "should fetch note"() {

    given:

    def expectedNoteFetched = new NoteDisplayDto(1L, "test title", "test content", LocalDate.now(), LocalDate.now(), 1L)

    when:

    def actualNoteFetched =
        this.mockMvc.perform(get("/notes/" + expectedNoteFetched.getNoteId()))
    then:

    actualNoteFetched
        .andExpect(status().isOk())
        .andExpect(jsonPath("title", is(expectedNoteFetched.getTitle())))
        .andExpect(jsonPath("content", is(expectedNoteFetched.getContent())))
        .andExpect(jsonPath("created", is(expectedNoteFetched.getCreated().toString())))
        .andExpect(jsonPath("modified", is(expectedNoteFetched.getModified().toString())))
        .andExpect(jsonPath("version", is(((int) expectedNoteFetched.getVersion()))))
  }

  def "should fetch all notes"() {

    given:

    def expectedNotesFetched =
        List.of(new NoteDisplayDto(1L, "test title", "test content", LocalDate.now(), LocalDate.now(), 1L))

    when:

    def actualNotesFetched =
        this.mockMvc.perform(get("/notes"))
    then:

    actualNotesFetched
        .andExpect(status().isOk())
        .andExpect(jsonPath("\$[0].title", is(expectedNotesFetched.get(0).getTitle())))
        .andExpect(jsonPath("\$[0].content", is(expectedNotesFetched.get(0).getContent())))
        .andExpect(jsonPath("\$[0].created", is(expectedNotesFetched.get(0).getCreated().toString())))
        .andExpect(jsonPath("\$[0].modified", is(expectedNotesFetched.get(0).getModified().toString())))
        .andExpect(jsonPath("\$[0].version", is(((int) expectedNotesFetched.get(0).getVersion()))))
  }

  def "should update note"() {

    given:

    def expectedNoteSaved = new NoteSaveDto("changed title", "changed content")

    when:

    def actualNoteSaved =
        this.mockMvc.perform(
            put("/notes/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(expectedNoteSaved)))
    then:

    actualNoteSaved
        .andExpect(status().isOk())
        .andExpect(jsonPath("title", is(expectedNoteSaved.getTitle())))
        .andExpect(jsonPath("content", is(expectedNoteSaved.getContent())))
        .andExpect(jsonPath("created", is(LocalDate.now().toString())))
        .andExpect(jsonPath("modified", is(LocalDate.now().toString())))
        .andExpect(jsonPath("version", is(2)))
  }

  def "should delete note"() {

    when:

    def actualNoteDeleted =
        this.mockMvc.perform(delete("/notes/1"))

    then:

    actualNoteDeleted
        .andExpect(status().isOk())
  }

  def "should fetch note history"() {

    given:

    def expectedNoteHistoryFetched =
        List.of(new NoteDisplayDto(1L, "test title", "test content", LocalDate.now(), LocalDate.now(), 1L),
            new NoteDisplayDto(1L, "changed title", "changed content", LocalDate.now(), LocalDate.now(), 2L))

    when:

    def actualNoteHistoryFetched =
        this.mockMvc.perform(get("/notes/1/history"))
    then:

    actualNoteHistoryFetched
        .andExpect(status().isOk())
        .andExpect(jsonPath("\$[0].title", is(expectedNoteHistoryFetched.get(0).getTitle())))
        .andExpect(jsonPath("\$[0].content", is(expectedNoteHistoryFetched.get(0).getContent())))
        .andExpect(jsonPath("\$[0].created", is(expectedNoteHistoryFetched.get(0).getCreated().toString())))
        .andExpect(jsonPath("\$[0].modified", is(expectedNoteHistoryFetched.get(0).getModified().toString())))
        .andExpect(jsonPath("\$[0].version", is(((int) expectedNoteHistoryFetched.get(0).getVersion()))))
        .andExpect(jsonPath("\$[1].title", is(expectedNoteHistoryFetched.get(1).getTitle())))
        .andExpect(jsonPath("\$[1].content", is(expectedNoteHistoryFetched.get(1).getContent())))
        .andExpect(jsonPath("\$[1].created", is(expectedNoteHistoryFetched.get(1).getCreated().toString())))
        .andExpect(jsonPath("\$[1].modified", is(expectedNoteHistoryFetched.get(1).getModified().toString())))
        .andExpect(jsonPath("\$[1].version", is(((int) expectedNoteHistoryFetched.get(1).getVersion()))))
  }

}