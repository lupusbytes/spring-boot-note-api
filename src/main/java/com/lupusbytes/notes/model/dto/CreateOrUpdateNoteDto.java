package com.lupusbytes.notes.model.dto;

import com.lupusbytes.notes.model.Tag;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

public class CreateOrUpdateNoteDto {
    @NotNull
    @Size(min = 1, message = "Title should be at least 1 character")
    private String title;

    @NotNull
    @Size(min = 1, message = "Text should be at least 1 character")
    private String text;

    private Set<Tag> tags = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
