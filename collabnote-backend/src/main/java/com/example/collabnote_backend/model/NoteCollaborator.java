package com.example.collabnote_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "note_collaborators")
public class NoteCollaborator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String role;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Note getNote() { return note; }
    public void setNote(Note note) { this.note = note; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}