package com.example.collabnote_backend.controller;

import com.example.collabnote_backend.dto.NoteRequest;
import com.example.collabnote_backend.dto.NoteResponse;
import com.example.collabnote_backend.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteRequest request) {
        return ResponseEntity.ok(noteService.createNote(request));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotes(@RequestParam Long ownerId) {
        return ResponseEntity.ok(noteService.getNotesByOwner(ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNote(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}