package com.example.gameslibrary.repository

import android.util.Log
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.data.models.ReviewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Filter
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

    fun loadGamesPaged(limit: Long, lastGame: GameModel?, genres: List<String>, controller: Boolean, sorting: Boolean, searchQuery: String): Flow<List<GameModel>> = callbackFlow {
        var query: Query = firestore.collection("games")

        if (searchQuery.isNotBlank()) {
            query = query
                .whereGreaterThanOrEqualTo("Title", searchQuery)
                .whereLessThan("Title", searchQuery + "\uf8ff")
                .orderBy("Title")
        }
         else if (sorting) {
            query = query.orderBy("release", Query.Direction.DESCENDING)
        } else {
            query = query.orderBy("Title")
        }

        query = query.limit(limit)

        if (lastGame != null) {
            query = if (searchQuery.isNotBlank() || !sorting) {
                query.startAfter(lastGame.Title)
            } else {
                query.startAfter(lastGame.release)
            }
        }

        query = query.whereEqualTo("controller", controller)

        if (genres.isNotEmpty()) {
            query = query.whereArrayContainsAny("Genre", genres)
        }


        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Firestore", "Ошибка загрузки игр: ${error.message}")
                close(error)
                return@addSnapshotListener
            }

            val games = snapshot?.documents?.mapNotNull { it.toObject(GameModel::class.java) }
                ?: emptyList()
            Log.d("Firestore", "Загружено игр: ${games.size}")
            trySend(games)
        }

        awaitClose { listener.remove() }
    }

    suspend fun loadGameByTitle(title: String, userId: String): GameModel? {
        return try {
            val snapshot = firestore.collection("games")
                .whereEqualTo("Title", title)
                .limit(1)
                .get()
                .await()

            val document = snapshot.documents.firstOrNull()
            val game = document?.toObject(GameModel::class.java)

            game?.let {
                it.documentId = document.id
                it.isWish = doesDocumentExist("wishlist", document.id, userId)
                it.isPlayed = doesDocumentExist("reviews", document.id, userId)
            }

            game
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка при получении игры: ${e.message}")
            null
        }
    }

    suspend fun loadGamesCount(genres: List<String> = emptyList(), isController: Boolean): Long {
        return try {
            var query: Query = firestore.collection("games").whereEqualTo("controller", isController)

            if (genres.isNotEmpty()) {
                query = query.whereArrayContainsAny("Genre", genres)
            }

            val countSnapshot = query.count().get(AggregateSource.SERVER).await()
            countSnapshot.count
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка при подсчёте игр: ${e.message}")
            0L
        }
    }

    suspend fun changeReviewToGame(gameId: String, userId: String, review: ReviewModel): Boolean {
        return try {
            val gameRef = firestore.collection("games")
                .document(gameId)
                .collection("reviews")
                .document(userId)

            val userRef = firestore.collection("users")
                .document(userId)
                .collection("reviews")
                .document(gameId)

            gameRef.set(review).await()
            userRef.set(emptyMap<String, Any>()).await()

            Log.d("Firestore", "Отзыв успешно добавлен")
            true
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка при добавлении отзыва: ${e.message}")
            false
        }
    }

    suspend fun changeWishlist(gameId: String, userId: String, isWishlist: Boolean): Boolean {
        return try {
            val gameRef = firestore.collection("games")
                .document(gameId)
                .collection("wishlist")
                .document(userId)

            val userRef = firestore.collection("users")
                .document(userId)
                .collection("wishlist")
                .document(gameId)

            if (!isWishlist) {
                gameRef.set(emptyMap<String, Any>()).await()
                userRef.set(emptyMap<String, Any>()).await()
            } else {
                gameRef.delete().await()
                userRef.delete().await()
            }

            Log.d("Firestore", "Отзыв успешно добавлен")
            true
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка при добавлении отзыва: ${e.message}")
            false
        }
    }

    suspend fun doesDocumentExist(collection: String, gameId: String, userId: String): Boolean {
        return try {
            val documentSnapshot = FirebaseFirestore.getInstance()
                .collection("games")
                .document(gameId)
                .collection(collection)
                .document(userId)
                .get()
                .await()

            documentSnapshot.exists()
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка проверки документа: ${e.message}")
            false
        }
    }

    suspend fun loadReviews(id: String): List<ReviewModel> {
        return try {
            val snapshot = firestore.collection("games")
                .document(id)
                .collection("reviews")
                .get()
                .await()

            // Преобразуем все документы в список объектов ReviewModel
            snapshot.documents.mapNotNull { document ->
                document.toObject(ReviewModel::class.java)  // Преобразуем документ в объект модели
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка при получении отзывов: ${e.message}")
            emptyList()  // Возвращаем пустой список в случае ошибки
        }
    }

}