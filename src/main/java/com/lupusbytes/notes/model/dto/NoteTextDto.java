package com.lupusbytes.notes.model.dto;

import com.lupusbytes.notes.model.document.Note;

public class NoteTextDto {
    private String text;

    public NoteTextDto(String text) {
        this.text = text;
    }

    public NoteTextDto(Note note) {
        this.text = note.getText();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
