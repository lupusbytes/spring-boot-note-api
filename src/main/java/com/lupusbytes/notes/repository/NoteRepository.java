package com.lupusbytes.notes.repository;

import com.lupusbytes.notes.model.Tag;
import com.lupusbytes.notes.model.document.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface NoteRepository extends MongoRepository<Note, String> {
    @Query("{id : ?0}")
    Optional<Note> findById(UUID id);

    // If the 'tags' parameter is an empty set, we should not perform filtering.
    // The second condition in this $or query ensures that.
    @Query(value="{ $or : [ { tags : { $in : ?0 } }, { $where: '\\'?0\\'.length == 2' } ] } ", fields = "{ 'title' : 1, 'tags' : 1, 'createdDate' : 1 }")
    Page<Note> findByTags(Set<Tag> tags, Pageable pageable);
}
