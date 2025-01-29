package com.example.gameslibrary.Repository

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gameslibrary.Domain.GameModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainRepository {
    private var db = Firebase.firestore.also {
        Log.d("FirebaseTest", "Firestore инициализирован")
    }

    fun loadUpcoming(): LiveData<MutableList<GameModel>> {
        Log.d("FirebaseTest", "Метод loadUpcoming() вызван") // Логируем вызов метода

        val listData = MutableLiveData<MutableList<GameModel>>()
        val usersRef = db.collection("games")

        usersRef.get()
            .addOnSuccessListener { result ->
                val lists = mutableListOf<GameModel>()
                for (document in result) {
                    val game = document.toObject(GameModel::class.java)
                    game?.let {
                        lists.add(it)
                        Log.d("FirebaseTest", "Загружена игра: $it")
                    }
                }
                listData.value = lists
            }
            .addOnFailureListener { exception ->
                Log.d("FirebaseTest", "Ошибка загрузки данных: ${exception.message}")
            }

        return listData
    }

}