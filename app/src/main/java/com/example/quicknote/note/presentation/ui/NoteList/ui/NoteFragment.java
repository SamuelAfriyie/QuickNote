package com.example.quicknote.note.presentation.ui.NoteList.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quicknote.R;
import com.example.quicknote.auth.domain.Note;
import com.example.quicknote.auth.presentation.login.ui.LoginActivity;
import com.example.quicknote.common.presentation.utils.NavHost;
import com.example.quicknote.databinding.FragmentNoteBinding;
import com.example.quicknote.databinding.FragmentSettingsBinding;
import com.example.quicknote.note.presentation.ui.NoteList.NoteAdapter;
import com.example.quicknote.note.presentation.ui.NoteList.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;

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

        noteAdapter = new NoteAdapter(noteArrayList);
        binding.recyclerViewNotes.setAdapter(noteAdapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.notifyDataSetChanged();
                noteArrayList.addAll(notes);
            }
        });
        binding.fabAddNote.setOnClickListener(v -> {});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}