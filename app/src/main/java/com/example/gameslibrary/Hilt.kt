package com.example.gameslibrary

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)  // Указываем, что это модуль для всего приложения

object Hilt {
    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore  // Возвращаем синглтон для FirebaseFirestore
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()  // FirebaseAuth
    }
}