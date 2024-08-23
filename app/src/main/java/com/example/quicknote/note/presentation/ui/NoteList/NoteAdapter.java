package com.example.quicknote.note.presentation.ui.NoteList;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quicknote.R;
import com.example.quicknote.common.presentation.utils.NavHost;
import com.example.quicknote.note.domain.Note;
import com.example.quicknote.note.presentation.ui.AddNote.ui.AddNoteActivity;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>  {

    private final List<Note> noteList;
    private final NoteViewModel noteViewModel;

    // Constructor to pass the data (list of notes)
    public NoteAdapter(List<Note> noteList, NoteViewModel noteViewModel) {
        this.noteList = noteList;
        this.noteViewModel = noteViewModel;
    }
    // Create the ViewHolder class
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView dateTextView;
        public ImageView deleteIcon;


        public NoteViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.descriptionTextView.setText(Html.fromHtml(note.getDescription(), Html.FROM_HTML_MODE_LEGACY).toString().substring(0, 5).concat("..."));
        holder.dateTextView.setText(note.getCreatedOn().toString());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = NavHost.navigate(v.getContext(), AddNoteActivity.class);
            intent.putExtra("NOTE_TO_UPDATE", note);
            v.getContext().startActivity(intent);
        });

        holder.deleteIcon.setOnClickListener(v -> {
            noteViewModel.deleteNote(note.getNoteId());
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}

