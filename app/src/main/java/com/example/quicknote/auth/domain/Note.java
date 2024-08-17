package com.example.quicknote.auth.domain;

import java.util.Date;

public class Note {
    private int noteId;
    private final String title;
    private final String description;
    private final Date createdOn;

    public Note(int noteId, String title, String description ) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
        this.createdOn = new Date();
    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.createdOn = new Date();
    }

//    public Note(int noteId, String title, String description, String date) {
//        this.noteId = noteId;
//        this.title = title;
//        this.description = description;
//        this.createdOn = new Date(date);
//    }


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
}
