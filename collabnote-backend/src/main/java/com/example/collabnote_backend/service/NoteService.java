package com.example.collabnote_backend.service;

import com.example.collabnote_backend.dto.NoteRequest;
import com.example.collabnote_backend.dto.NoteResponse;
import com.example.collabnote_backend.model.Note;
import com.example.collabnote_backend.model.User;
import com.example.collabnote_backend.repository.NoteRepository;
import com.example.collabnote_backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public NoteResponse createNote(NoteRequest request) {
        User owner = userRepository.findById(request.ownerId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Note note = new Note();
        note.setTitle(request.title());
        note.setOwner(owner);
        note.setCreatedAt(Instant.now());
        note.setUpdatedAt(Instant.now());

        Note saved = noteRepository.save(note);
        return toResponse(saved);
    }

    public List<NoteResponse> getNotesByOwner(Long ownerId) {
        return noteRepository.findByOwnerId(ownerId).stream()
            .map(this::toResponse)
            .toList();
    }

    public NoteResponse getNote(Long id) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        return toResponse(note);
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    private NoteResponse toResponse(Note note) {
        return new NoteResponse(note.getId(), note.getTitle(), note.getOwner().getId(),
            note.getCreatedAt(), note.getUpdatedAt());
    }
}