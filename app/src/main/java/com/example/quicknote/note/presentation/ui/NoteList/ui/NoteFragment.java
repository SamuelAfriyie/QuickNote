package com.example.quicknote.note.presentation.ui.NoteList.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quicknote.common.presentation.utils.NavHost;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.Failure;
import com.example.quicknote.databinding.FragmentNoteBinding;
import com.example.quicknote.note.domain.Note;
import com.example.quicknote.note.presentation.ui.AddNote.ui.AddNoteActivity;
import com.example.quicknote.note.presentation.ui.NoteList.NoteAdapter;
import com.example.quicknote.note.presentation.ui.NoteList.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    public NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;

    public NoteFragment(NoteViewModel noteViewModel) {
        this.noteViewModel = noteViewModel;
    }

    public NoteFragment() {
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Note> noteArrayList = new ArrayList<>();
        recyclerView = binding.recyclerViewNotes;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        noteAdapter = new NoteAdapter(noteArrayList, noteViewModel);
        binding.recyclerViewNotes.setAdapter(noteAdapter);

        noteViewModel.getNotes().observe(getViewLifecycleOwner(), v -> observeNoteData(v, noteArrayList));
         noteViewModel.deletionStatus.observe(getViewLifecycleOwner(), v -> {
            if (v.isSuccess()) {
                noteViewModel.fetchAllNotes();
                Toast.makeText(getContext(), "Note deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });

        binding.fabAddNote.setOnClickListener(v -> {
            NavHost.navigateTo(this.getContext(), AddNoteActivity.class);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void observeNoteData(Response<List<Note>, Failure> v, List<Note> noteArrayList) {
        noteAdapter.notifyDataSetChanged();
        if (v.isSuccess()) {
            Response.Success<List<Note>, Failure> success = (Response.Success<List<Note>, Failure>) v;
            noteArrayList.clear();
            noteArrayList.addAll(success.getValue());
        } else {
            Response.Failure<List<Note>, Failure> failure = (Response.Failure<List<Note>, Failure>) v;
            Failure res = failure.getValue();
            Toast.makeText(getContext(), res.getRES_MSG(), Toast.LENGTH_SHORT).show();
        }
    }

}