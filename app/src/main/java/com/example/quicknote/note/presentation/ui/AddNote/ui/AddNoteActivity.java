package com.example.quicknote.note.presentation.ui.AddNote.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.quicknote.R;
import com.example.quicknote.common.presentation.utils.NavHost;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.Failure;
import com.example.quicknote.databinding.ActivityAddNotesBinding;
import com.example.quicknote.note.domain.Note;
import com.example.quicknote.note.presentation.ui.AddNote.AddNoteViewModel;
import com.example.quicknote.note.presentation.ui.NoteActivity;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import jp.wasabeef.richeditor.RichEditor;

@AndroidEntryPoint
public class AddNoteActivity extends AppCompatActivity {

    private ActivityAddNotesBinding binding;
    private RichEditor mEditor;
    private AddNoteViewModel addNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mEditor = findViewById(R.id.editor);

        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        Note notToUpdate = (Note) getIntent().getSerializableExtra("NOTE_TO_UPDATE");

        if (notToUpdate != null) {
            binding.txtTitle.setText(notToUpdate.getTitle());
            binding.editor.setHtml(notToUpdate.getDescription());
        }
        binding.actionUndo.setOnClickListener(v -> mEditor.undo());
        binding.actionRedo.setOnClickListener(v -> mEditor.redo());
        binding.actionPrev.setOnClickListener(c -> NavHost.navigateTo(this, NoteActivity.class));
        binding.btnDone.setOnClickListener(c -> {
            String title = Objects.requireNonNull(binding.txtTitle.getText()).toString();
            String body = Objects.requireNonNull(binding.editor.getHtml());
            addNoteViewModel.AddNoteEvent(new Note(notToUpdate == null ? 0: notToUpdate.getNoteId(), title, body));
        });

        addNoteViewModel.addNoteEventListener.observe(this, v -> {
            if (v.isSuccess()) {
                NavHost.navigateTo(this, NoteActivity.class);
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            } else {
                Response.Failure<Integer, Failure> failure = (Response.Failure<Integer, Failure>) v;
                Failure res = failure.getValue();
                Toast.makeText(this, res.getRES_MSG(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}