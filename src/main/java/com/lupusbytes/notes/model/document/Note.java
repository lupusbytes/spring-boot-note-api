package com.lupusbytes.notes.model.document;

import com.lupusbytes.notes.model.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Document("Note")
public class Note {
    @Id
    private UUID id;
    private String title;
    private String text;
    private Set<Tag> tags;
    private Date createdDate;

    public Note() {
        id = UUID.randomUUID();
        createdDate = new Date();
    }

    public Note(UUID id, String title, String text, Set<Tag> tags, Date createdDate) {
        super();
        this.id = id;
        this.title = title;
        this.text = text;
        this.tags = tags;
        this.createdDate = createdDate;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
