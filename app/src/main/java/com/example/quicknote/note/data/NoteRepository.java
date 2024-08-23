package com.example.quicknote.note.data;

import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.failures.Failure;
import com.example.quicknote.note.domain.Note;

import java.util.List;

public interface NoteRepository {
    public Response<Integer, Failure> createNote( Note note);
    public Response<Integer, Failure> updateNote(Note user);
    public Response<List<Note>, Failure> fetchAll(int userId);
    public Response<Integer, Failure> fetchById(int noteId);
    public Response<List<Note>, Failure> filterByTitle(int userId, String title);
    public Response<Integer, Failure> deleteNote(int noteId);
}
