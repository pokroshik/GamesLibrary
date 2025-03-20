package com.example.gameslibrary.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.data.models.ReviewModel
import com.example.gameslibrary.data.models.UserModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
){

    suspend fun loadUserByUid(uid: String): UserModel? {
        return try {
            val snapshot = firestore.collection("users")
                .document(uid)
                .get()
                .await()

            if (snapshot.exists()) {
                snapshot.toObject(UserModel::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Ошибка загрузки пользователя", e)
            null
        }
    }

    suspend fun loadUserGames(userId: String, isReview: Boolean): List<GameModel> {
        return try {
            val path = if (isReview) "reviews" else "wishlist"
            val userGamesSnapshot = firestore.collection("users")
                .document(userId)
                .collection(path)
                .get()
                .await()

            val gameIds = userGamesSnapshot.documents.mapNotNull { it.id }

            if (gameIds.isEmpty()) return emptyList()

            val gamesSnapshot = firestore.collection("games")
                .whereIn(FieldPath.documentId(), gameIds)
                .get()
                .await()

            gamesSnapshot.documents.mapNotNull { it.toObject(GameModel::class.java) }
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка при загрузке игр пользователя", e)
            emptyList()  // В случае ошибки возвращаем пустой список
        }
    }

    suspend fun getUserReviewedGames(userId: String): List<String> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("reviews")
                .get()
                .await()

            snapshot.documents.mapNotNull { it.id }  // Получаем список gameId
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка при загрузке списка игр: ${e.message}")
            emptyList()
        }
    }

    suspend fun getUserReviews(userId: String): List<ReviewModel> {
        val gameIds = getUserReviewedGames(userId)  // Получаем список игр
        val reviews = mutableListOf<ReviewModel>()

        gameIds.forEach { gameId ->
            try {
                val gameSnapshot = firestore.collection("games")
                    .document(gameId)
                    .get()
                    .await()

                val gameTitle = gameSnapshot.getString("Title") ?: "Неизвестная игра"  // Берём название

                val snapshot = firestore.collection("games")
                    .document(gameId)
                    .collection("reviews")
                    .document(userId)
                    .get()
                    .await()

                val review = snapshot.toObject(ReviewModel::class.java)
                if (review != null) {
                    reviews.add(review.copy(gameTitle = gameTitle))
                }
            } catch (e: Exception) {
                Log.e("Firestore", "Ошибка при загрузке отзыва: ${e.message}")
            }
        }
        return reviews
    }

    fun signInWithEmail(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    fun signUpWithEmail(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        createUserDocument(it.uid, email)
                    }
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    suspend fun signOut(): Boolean {
        return try {
            FirebaseAuth.getInstance().signOut()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    suspend fun deleteUser(uid: String) {
        val documentRef = firestore.collection("users")
            .document(uid)

        try {
            documentRef.delete().await()
            Log.d("Firestore", "Документ успешно удален!")
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка удаления документа: ${e.message}", e)
        }
        auth.currentUser?.delete()
    }

/*    fun reauthenticateAndDeleteUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val user = auth.currentUser

        if (user != null) {
            val credential = EmailAuthProvider.getCredential(email, password)

            user.reauthenticate(credential)
                .addOnSuccessListener {
                    deleteUser()
                }
                .addOnFailureListener { exception -> onFailure(exception) }
        } else {
            onFailure(Exception("Пользователь не авторизован"))
        }
    }*/

    private fun createUserDocument(uid: String, email: String) {
        val userDocument = hashMapOf(
            "createdAt" to Timestamp(System.currentTimeMillis()),
            "lastVisited" to Timestamp(System.currentTimeMillis()),
            "birthday" to null,
            "login" to email,
            "firstName" to null,
            "lastName" to null,
            "bio" to null,
            "country" to null,
            "gender" to null,
            "steamProfile" to null,
            "favoriteGames" to arrayListOf<String>(),
            "wishlistGames" to arrayListOf<String>(),
            "reviewsGames" to arrayListOf<String>(),
        )

        firestore.collection("users")
            .document(uid)
            .set(userDocument)
            .addOnSuccessListener {
                Log.d("AuthRepository", "User document created with ID: $uid")
            }
            .addOnFailureListener { e ->
                Log.w("AuthRepository", "Error creating user document", e)
            }
    }

    fun getCurrentUserUid(): String? {
        return auth.currentUser?.uid
    }

    suspend fun updateUser(uid: String ,user: UserModel): Boolean {
        return try {

            val userRef = firestore.collection("users")
                .document(uid)

            userRef.set(user, SetOptions.merge()).await()

            Log.d("Firestore", "Отзыв успешно добавлен")
            true
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка при добавлении отзыва: ${e.message}")
            false
        }
    }

    suspend fun updateTime(uid: String, time: Long): Boolean {
        return try {

            val userRef = firestore.collection("users")
                .document(uid)

            userRef.update("lastVisited", Timestamp(time)).await()

            Log.d("Firestore", "Отзыв успешно добавлен")
            true
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка при добавлении отзыва: ${e.message}")
            false
        }
    }

    suspend fun getUserGames(userId: String, collection: String): List<String> {
        val db = FirebaseFirestore.getInstance()
        val wishlistRef = db.collection("users").document(userId).collection(collection)

        return try {
            val snapshot = wishlistRef.get().await()
            snapshot.documents.mapNotNull { it.id }
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка загрузки wishlist", e)
            emptyList()
        }
    }

}