package com.example.quicknote.note.di;

import android.content.Context;

import com.example.quicknote.note.data.NoteRepository;
import com.example.quicknote.note.data.NoteRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class NoteRepositoryModule {
    @Provides
    public NoteRepository provideNoteRepository(@ApplicationContext Context context) {
        return new NoteRepositoryImpl(context);
    }
}
