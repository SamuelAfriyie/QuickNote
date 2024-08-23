package com.example.quicknote.note.domain;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;


public class Note implements Serializable {
    private int noteId;
    private int noteUserId;
    private String title;
    private String description;
    private Date createdOn;

    public Note() {
    }

    public Note(int noteId, String title, String description) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
        this.createdOn = new Date();
    }
    public Note(int noteId, int noteUserId, String title, String description) {
        this.noteId = noteId;
        this.noteUserId = noteUserId;
        this.title = title;
        this.description = description;
        this.createdOn = new Date();
    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.createdOn = new Date();
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getNoteUserId() {
        return noteUserId;
    }

    public void setNoteUserId(int noteUserId) {
        this.noteUserId = noteUserId;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteUserId=" + noteUserId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
