package com.example.quicknote.note.presentation.ui.NoteList;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quicknote.common.presentation.auth.AuthStateViewModel;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.Failure;
import com.example.quicknote.note.data.NoteRepository;
import com.example.quicknote.note.domain.Note;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NoteViewModel extends ViewModel {
    private final NoteRepository noteRepository;
    private final MutableLiveData<Response<List<Note>, Failure>> notes = new MutableLiveData<>();
    private final MutableLiveData<Response<Integer, Failure>> _deletionStatus = new MutableLiveData<>();
    public final LiveData<Response<Integer, Failure>> deletionStatus = _deletionStatus;

    @Inject
    public NoteViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        fetchAllNotes();
    }

    public LiveData<Response<List<Note>, Failure>> getNotes() {
        return notes;
    }


    public void fetchAllNotes() {
        notes.setValue(noteRepository.fetchAll(AuthStateViewModel.userCache.getUserId()));
    }
    public void filterAllNotes(String filteredText) {
        notes.setValue(noteRepository.filterByTitle(AuthStateViewModel.userCache.getUserId(), filteredText));
    }

    public void deleteNote(int noteId) {
        _deletionStatus.setValue(noteRepository.deleteNote(noteId));
    }
}
