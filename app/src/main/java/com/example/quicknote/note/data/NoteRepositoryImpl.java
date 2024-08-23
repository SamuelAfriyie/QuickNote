package com.example.quicknote.note.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quicknote.auth.data.failures.DatabaseNotFound;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.data.Dao;
import com.example.quicknote.core.failures.Failure;
import com.example.quicknote.note.domain.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {
    private final Dao noteDao;

    public NoteRepositoryImpl(Context context) {
        this.noteDao = new Dao(context);
    }

    @Override
    public Response<Integer, Failure> createNote(Note note) {
        try {

            SQLiteDatabase db = noteDao.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Dao.COLUMN_NOTE_TITLE, note.getTitle());
            values.put(Dao.COLUMN_NOTE_DESC, note.getDescription());
            values.put(Dao.COLUMN_NOTE_USER_ID, note.getNoteUserId());
            values.put(Dao.COLUMN_NOTE_DATE, note.getCreatedOn().toString());
            Log.d("DatabaseDebug", "Inserting user into database...");
            long result = db.insert(Dao.TABLE_NOTES, null, values);
            Log.d("DatabaseDebug", "Insert result: " + result);
            db.close();
            return result != -1 ? Response.success(1) : Response.failure(new DatabaseNotFound("FailedToCreate", "Note creation failed"));
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }

    @Override
    public Response<Integer, Failure> updateNote(Note note) {
        try {
            SQLiteDatabase db = noteDao.getWritableDatabase();
            ContentValues values = new ContentValues();

            // Add updated values to the ContentValues
            values.put(Dao.COLUMN_NOTE_TITLE, note.getTitle());
            values.put(Dao.COLUMN_NOTE_DESC, note.getDescription());
            values.put(Dao.COLUMN_NOTE_DATE, note.getCreatedOn().toString());

            // Define the WHERE clause and arguments
            String whereClause = Dao.COLUMN_NOTE_ID + " = ?";
            String[] whereArgs = {String.valueOf(note.getNoteId())};

            // Log the start of the update operation
            Log.d("DatabaseDebug", "Updating note with noteId: " + note.getNoteId());
            int rowsAffected = db.update(Dao.TABLE_NOTES, values, whereClause, whereArgs);
            Log.d("DatabaseDebug", "Rows affected: " + rowsAffected);
            db.close();

            return Response.success(rowsAffected);
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }

    @Override
    public Response<List<Note>, Failure> fetchAll(int userId) {
        try {
            List<Note> notes = new ArrayList<>();
            SQLiteDatabase db = noteDao.getReadableDatabase();

            // Define the query
            String[] columns = {
                    Dao.COLUMN_NOTE_ID,
                    Dao.COLUMN_NOTE_TITLE,
                    Dao.COLUMN_NOTE_DESC,
                    Dao.COLUMN_NOTE_DATE
            };

            String selection = Dao.COLUMN_NOTE_USER_ID + " = ?";
            String[] selectionArgs = {String.valueOf(userId)};

            Cursor cursor = db.query(
                    Dao.TABLE_NOTES,   // The table to query
                    columns,           // The columns to return
                    selection,         // The columns for the WHERE clause
                    selectionArgs,     // The values for the WHERE clause
                    null,              // Don't group the rows
                    null,              // Don't filter by row groups
                    null               // The sort order
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // Create a new Note object for each row
                    int noteId = cursor.getInt(cursor.getColumnIndexOrThrow(Dao.COLUMN_NOTE_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_NOTE_TITLE));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_NOTE_DESC));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_NOTE_DATE));

                    Note note = new Note();
                    note.setNoteId(noteId);
                    note.setTitle(title);
                    note.setDescription(description);
                    note.setCreatedOn(new Date(date)); // Assuming setCreatedOn accepts a String

                    // Add the Note object to the list
                    notes.add(note);
                }
                cursor.close(); // Close the cursor after use
            }

            db.close(); // Close the database after use

            return Response.success(notes);
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }

    @Override
    public Response<Integer, Failure> fetchById(int noteId) {
        return null;
    }

    @Override
    public Response<List<Note>, Failure> filterByTitle(int userId, String titleFilter) {
        try {
            List<Note> notes = new ArrayList<>();
            SQLiteDatabase db = noteDao.getReadableDatabase();

            // Define the query
            String[] columns = {
                    Dao.COLUMN_NOTE_ID,
                    Dao.COLUMN_NOTE_TITLE,
                    Dao.COLUMN_NOTE_DESC,
                    Dao.COLUMN_NOTE_DATE
            };

            String selection = Dao.COLUMN_NOTE_USER_ID + " = ? AND " + Dao.COLUMN_NOTE_TITLE + " LIKE ?";
            String[] selectionArgs = {
                    String.valueOf(userId),
                    "%" + titleFilter + "%" // Use LIKE to filter by title
            };

            Cursor cursor = db.query(
                    Dao.TABLE_NOTES,   // The table to query
                    columns,           // The columns to return
                    selection,         // The columns for the WHERE clause
                    selectionArgs,     // The values for the WHERE clause
                    null,              // Don't group the rows
                    null,              // Don't filter by row groups
                    null               // The sort order
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // Create a new Note object for each row
                    int noteId = cursor.getInt(cursor.getColumnIndexOrThrow(Dao.COLUMN_NOTE_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_NOTE_TITLE));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_NOTE_DESC));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_NOTE_DATE));

                    Note note = new Note();
                    note.setNoteId(noteId);
                    note.setTitle(title);
                    note.setDescription(description);
                    note.setCreatedOn(new Date(date)); // Assuming setCreatedOn accepts a String

                    // Add the Note object to the list
                    notes.add(note);
                }
                cursor.close(); // Close the cursor after use
            }

            db.close(); // Close the database after use

            return Response.success(notes);
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }

    @Override
    public Response<Integer, Failure> deleteNote(int noteId) {
        try {
            SQLiteDatabase db = noteDao.getWritableDatabase();

            // Define 'where' part of query.
            String selection = Dao.COLUMN_NOTE_ID + " = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = { String.valueOf(noteId) };

            // Issue SQL statement.
            int deletedRows = db.delete(Dao.TABLE_NOTES, selection, selectionArgs);

            Log.d("DatabaseDebug", "Deleted rows: " + deletedRows);

            db.close();
            return Response.success(deletedRows);
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }
}
