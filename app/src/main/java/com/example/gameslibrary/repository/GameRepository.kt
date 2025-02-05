package com.example.gameslibrary.repository

import android.util.Log
import com.example.gameslibrary.data.models.GameModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getGamesPaged(limit: Long, lastGame: GameModel?): Flow<List<GameModel>> = callbackFlow {
        var query = firestore.collection("games")
            .orderBy("release", Query.Direction.DESCENDING)
            .limit(limit)

        if (lastGame != null) {
            query = query.startAfter(lastGame.release)
        }

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Firestore123", "Ошибка загрузки игр: ${error.message}")
                close(error)
                return@addSnapshotListener
            }

            val games = snapshot?.documents?.mapNotNull { it.toObject(GameModel::class.java) } ?: emptyList()
            Log.d("Firestore123", "Загружено игр: ${games.size}")
            trySend(games)
        }

        awaitClose { listener.remove() }
    }


    suspend fun loadGameByTitle(title: String): GameModel? {
        return try {
            val snapshot = firestore.collection("games")
                .whereEqualTo("Title", title)
                .limit(1)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.toObject(GameModel::class.java)
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка при получении игры: ${e.message}")
            null
        }
    }
}