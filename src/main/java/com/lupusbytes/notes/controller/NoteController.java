package com.lupusbytes.notes.controller;

import com.lupusbytes.notes.model.Tag;
import com.lupusbytes.notes.model.document.Note;
import com.lupusbytes.notes.model.dto.NoteDetailsDto;
import com.lupusbytes.notes.model.dto.CreateOrUpdateNoteDto;
import com.lupusbytes.notes.model.dto.NoteTextDto;
import com.lupusbytes.notes.model.dto.PagedResponse;
import com.lupusbytes.notes.repository.NoteRepository;
import com.lupusbytes.notes.service.WordCounter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping()
    public PagedResponse<NoteDetailsDto> getNotePage(
            @RequestParam(name = "tags", required = false) Set<Tag> tags,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize) {
        if (tags == null) {
            tags = new HashSet<>();
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Note> notes = noteRepository.findByTags(tags, pageable);

        return new PagedResponse(
                notes.getContent().stream().map(NoteDetailsDto::new).toList(),
                notes.getNumber(),
                notes.getTotalPages(),
                notes.getTotalElements());
    }

    @PostMapping
    public Note createNote(@Valid @RequestBody CreateOrUpdateNoteDto requestBody) {
        Note note = new Note();
        note.setTitle(requestBody.getTitle());
        note.setText(requestBody.getText());
        note.setTags(requestBody.getTags());

        noteRepository.save(note);

        return note;
    }

    @GetMapping("/{id}")
    public NoteTextDto getNoteTextById(@PathVariable("id") UUID noteId) {
        Optional<Note> note = noteRepository.findById(noteId);

        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new NoteTextDto(note.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> createOrUpdateNoteById(
            @PathVariable("id") UUID noteId,
            @Valid @RequestBody CreateOrUpdateNoteDto requestBody) {
        Optional<Note> noteQueryResponse = noteRepository.findById(noteId);

        HttpStatus httpStatus;
        Note note;
        if (noteQueryResponse.isEmpty()){
            httpStatus = HttpStatus.CREATED;
            note = new Note();
            note.setId(noteId);
        } else {
            httpStatus = HttpStatus.OK;
            note = noteQueryResponse.get();
        }

        note.setTitle(requestBody.getTitle());
        note.setText(requestBody.getText());
        note.setTags(requestBody.getTags());

        noteRepository.save(note);

        return ResponseEntity.status(httpStatus).body(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNoteById(@PathVariable("id") UUID noteId) {
        Optional<Note> note = noteRepository.findById(noteId);

        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        noteRepository.delete(note.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/stats")
    public Map<String, Integer> getNoteWordCountById(@PathVariable("id") UUID noteId) {
        Optional<Note> note = noteRepository.findById(noteId);

        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return WordCounter.GetWordCountMap(note.get().getText());
    }
}
