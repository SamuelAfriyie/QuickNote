package com.example.quicknote.auth.di;

import android.content.Context;

import com.example.quicknote.auth.data.repo.AuthRepository;
import com.example.quicknote.auth.data.repo.AuthRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AuthRepositoryModule {
    @Provides
    public AuthRepository provideAuthRepository(@ApplicationContext Context context) {
        return new AuthRepositoryImpl(context);
    }
}
