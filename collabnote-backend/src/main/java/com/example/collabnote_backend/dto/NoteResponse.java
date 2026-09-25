package com.example.collabnote_backend.dto;

import java.time.Instant;

public record NoteResponse(Long id, String title, Long ownerId, Instant createdAt, Instant updatedAt) {}