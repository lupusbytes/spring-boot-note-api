package com.lupusbytes.notes.controller;

import com.lupusbytes.notes.model.document.Note;
import com.lupusbytes.notes.repository.NoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NoteControllerTests {
    private static UUID testNoteId = UUID.fromString("d0000000-0000-0000-0000-00000000000d");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteController noteController;

    @Test
    public void getNotePageShouldReturnPagedResponse() throws Exception {
        // Arrange
        Page<Note> page = new PageImpl<>(new ArrayList<>());
        Mockito.when(noteRepository.findByTags(any(), any())).thenReturn(page);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/notes"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.records").isArray())
                .andExpect(jsonPath("$.currentPage").isNumber())
                .andExpect(jsonPath("$.totalPages").isNumber())
                .andExpect(jsonPath("$.totalRecords").isNumber());
    }

    @Test
    public void createNoteShouldSaveNoteOnValidRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/notes/")
                        .content("{\"title\":\"Foo Bar\",\"text\":\"Hello World\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title").value("Foo Bar"))
                .andExpect(jsonPath("$.text").value("Hello World"));

        verify(noteRepository, times(1)).save(any());
    }

    @Test
    public void createNoteShouldReturnBadRequestWhenTitleIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/notes/")
                        .content("{\"text\":\"Hello World\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void createNoteShouldReturnBadRequestWhenTextIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/notes/")
                        .content("{\"title\":\"Foo Bar\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void getNoteTextByIdShouldReturnNoteText() throws Exception {
        // Arrange
        Note note = new Note();
        note.setId(testNoteId);
        note.setText("Hello World");

        Mockito.when(noteRepository.findById(testNoteId)).thenReturn(Optional.of(note));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/notes/" + testNoteId))
                .andExpect(status().is(200))
                .andExpect(content().json("{\"text\":\"Hello World\"}"));
    }

    @Test
    public void getNoteTextByIdIdShouldReturnNotFound() throws Exception {
        // Arrange
        Mockito.when(noteRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/notes/" + UUID.randomUUID().toString()))
                .andExpect(status().is(404));
    }

    @Test
    public void createOrUpdateNoteByIdShouldCreateNote() throws Exception {
        // Arrange
        Mockito.when(noteRepository.findById(testNoteId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/notes/" + testNoteId)
                        .content("{\"title\":\"Foo Bar\",\"text\":\"Hello World\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.title").value("Foo Bar"))
                .andExpect(jsonPath("$.text").value("Hello World"));

        verify(noteRepository, times(1)).save(any());
    }

    @Test
    public void createOrUpdateNoteByIdShouldUpdateNote() throws Exception {
        // Arrange
        Note note = new Note();
        note.setId(testNoteId);

        Mockito.when(noteRepository.findById(testNoteId)).thenReturn(Optional.of(note));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/notes/" + testNoteId)
                        .content("{\"title\":\"Foo Bar\",\"text\":\"Hello World\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.title").value("Foo Bar"))
                .andExpect(jsonPath("$.text").value("Hello World"));

        verify(noteRepository, times(1)).save(any());
    }

    @Test
    public void createOrUpdateNoteByIdShouldReturnBadRequestWhenTitleIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/notes/" + testNoteId)
                        .content("{\"text\":\"Hello World\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void createOrUpdateNoteByIdShouldReturnBadRequestWhenTextIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/notes/" + testNoteId)
                        .content("{\"title\":\"Foo Bar\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void deleteNoteByIdShouldDeleteNote() throws Exception {
        // Arrange
        Note note = new Note();
        note.setId(testNoteId);

        Mockito.when(noteRepository.findById(testNoteId)).thenReturn(Optional.of(note));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/notes/" + testNoteId))
                .andExpect(status().is(204));

        verify(noteRepository, times(1)).delete(any());
    }

    @Test
    public void deleteNoteByIdShouldReturnNotFound() throws Exception {
        // Arrange
        Mockito.when(noteRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/notes/" + UUID.randomUUID()))
                .andExpect(status().is(404));
    }

    @Test
    public void getNoteWordCountByIdShouldReturnContract() throws Exception {
        // Arrange
        Note note = new Note();
        note.setId(testNoteId);
        note.setText("Hello World");

        Mockito.when(noteRepository.findById(testNoteId)).thenReturn(Optional.of(note));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/notes/" + testNoteId + "/stats"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.hello").value(1))
                .andExpect(jsonPath("$.world").value(1));
    }

    @Test
    public void getNoteWordCountByIdShouldReturnNotFound() throws Exception {
        // Arrange
        Mockito.when(noteRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/notes/" + UUID.randomUUID() + "/stats"))
                .andExpect(status().is(404));
    }

}
