package com.example.quicknote.note.presentation.ui.NoteList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quicknote.auth.domain.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends ViewModel {
    private final MutableLiveData<List<Note>> notes = new MutableLiveData<>();

    public NoteViewModel() {
        loadNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    private void loadNotes() {
        // You can fetch this data from SQLite database or repository
        List<Note> sampleNotes = new ArrayList<>();
        sampleNotes.add(new Note(1, "Note Title 1", "Description 1"));
        sampleNotes.add(new  Note(2, "Note Title 2", "Description 1"));
        sampleNotes.add(new  Note(3, "Note Title 3", "Description 1"));
        sampleNotes.add(new  Note(4, "Note Title 4", "Description 1"));
        sampleNotes.add(new Note(5, "Note Title 5", "Description 2"));
        sampleNotes.add(new Note(6, "Note Title 6", "Description 2"));
        sampleNotes.add(new Note(7, "Note Title 7", "Description 2"));

        notes.setValue(sampleNotes);
    }
}
