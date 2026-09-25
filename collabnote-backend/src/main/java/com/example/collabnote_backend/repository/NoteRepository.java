package com.example.collabnote_backend.repository;

import com.example.collabnote_backend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByOwnerId(Long ownerId);
}