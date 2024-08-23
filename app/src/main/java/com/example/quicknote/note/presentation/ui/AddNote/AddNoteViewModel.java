package com.example.quicknote.note.presentation.ui.AddNote;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quicknote.common.presentation.auth.AuthStateViewModel;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.Failure;
import com.example.quicknote.note.data.NoteRepository;
import com.example.quicknote.note.domain.Note;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddNoteViewModel extends ViewModel {

    private final NoteRepository repo;
    private final MutableLiveData<Response<Integer, Failure>> _addNoteEventListener = new MutableLiveData<>();
    public LiveData<Response<Integer, Failure>> addNoteEventListener = _addNoteEventListener;

    @Inject
    public AddNoteViewModel(NoteRepository repo) {
        this.repo = repo;
    }

    public void AddNoteEvent(Note note) {
        note.setNoteUserId(AuthStateViewModel.userCache.getUserId()); //setting the current userId before saving the record
        Log.d("Creating Note", note.toString());
        if (note.getNoteId() == 0) {
            _addNoteEventListener.setValue(repo.createNote(note));
        } else {
            _addNoteEventListener.setValue(repo.updateNote(note));
        }
    }
}
