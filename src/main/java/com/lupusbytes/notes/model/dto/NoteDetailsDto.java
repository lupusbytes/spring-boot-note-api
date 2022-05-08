package com.lupusbytes.notes.model.dto;

import com.lupusbytes.notes.model.Tag;
import com.lupusbytes.notes.model.document.Note;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class NoteDetailsDto {
    private UUID id;
    private String title;
    private Date createdDate;
    private Set<Tag> tags;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public NoteDetailsDto() {
    }

    public NoteDetailsDto(UUID id, String title, Date createdDate, Set<Tag> tags) {
        super();
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.createdDate = createdDate;
    }

    public NoteDetailsDto(Note note) {
        super();
        this.id = note.getId();
        this.title = note.getTitle();
        this.tags = note.getTags();
        this.createdDate = note.getCreatedDate();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
