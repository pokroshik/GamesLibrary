package com.example.gameslibrary.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.data.models.ReviewModel
import com.example.gameslibrary.data.models.UserModel
import com.example.gameslibrary.repository.AuthRepository
import com.example.gameslibrary.ui.screens.LoginActivity
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.system.exitProcess

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _userAcc = MutableStateFlow<FirebaseUser?>(null)
    val userAcc: StateFlow<FirebaseUser?> = _userAcc.asStateFlow()

    private var _mainUser = MutableStateFlow<UserModel?>(null)
    var mainUser: StateFlow<UserModel?> = _mainUser.asStateFlow()

    private var _user = MutableStateFlow<UserModel?>(null)
    var user: StateFlow<UserModel?> = _user.asStateFlow()

    private val _wishlist = MutableStateFlow<List<GameModel>>(emptyList())
    val wishlist: StateFlow<List<GameModel>> = _wishlist.asStateFlow()

    private val _reviews = MutableStateFlow<List<GameModel>>(emptyList())
    val reviews: StateFlow<List<GameModel>> = _reviews.asStateFlow()

    private val _reviews1 = MutableStateFlow<List<ReviewModel>>(emptyList())
    val reviews1: StateFlow<List<ReviewModel>> = _reviews1.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    fun loadUserProfile() {
        viewModelScope.launch {
            val user = repository.getCurrentUser()
            _userAcc.value = user
        }
    }

    fun signIn(email: String, password: String) {
        repository.signInWithEmail(email, password) { success, errorMessage ->
            if (success) {
                viewModelScope.launch {
                    _user.value = repository.loadUserByUid(getUserUid()!!)
                    _error.value = null
                    _userAcc.value = repository.getCurrentUser()
                }
            } else {
                _error.value = errorMessage
            }
        }
    }

    fun signUp(email: String, password: String) {
        repository.signUpWithEmail(email, password) { success, errorMessage ->
            if (success) {
                viewModelScope.launch {
                    _user.value = repository.loadUserByUid(getUserUid()!!)
                    _error.value = null
                    _userAcc.value = repository.getCurrentUser()
                }
            } else {
                _error.value = errorMessage
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            repository.signOut()
            _user.value = null

        }
    }

    fun getUserUid(): String? {
        return repository.getCurrentUserUid()
    }

    fun updateUserInfo(updatedUser: UserModel) {
        viewModelScope.launch {
            repository.updateUser(getUserUid()!!,updatedUser)
            _user.value = user.value
            _mainUser.value = _user.value
        }
    }

    fun deleteAccount() {
        //email: String,
       // password: String,
      //  onSuccess: () -> Unit,
     //   onFailure: (Exception) -> Unit
    //) {
        viewModelScope.launch {
            repository.deleteUser(getUserUid()!!)//reauthenticateAndDeleteUser(email, password, onSuccess, onFailure)
            _user.value = null
            _userAcc.value = null
        }

    }

    fun loadUserGames(userId: String) {
        viewModelScope.launch {
            var games = repository.loadUserGames(userId, true)
            _reviews.value = games

            games = repository.loadUserGames(userId, false)
            _wishlist.value = games

            _reviews1.value = repository.getUserReviews(userId)
        }
    }

    fun loadUser(uid: String) {
        viewModelScope.launch {
            val user = repository.loadUserByUid(uid)
            _user.value = user
        }
    }

    fun loadMainUser() {
        viewModelScope.launch {
            val user1 = repository.loadUserByUid(getUserUid()!!)
            _mainUser.value = user1
        }
    }

    fun updateLoginTime(isExit: Boolean) {
        viewModelScope.launch {
            val calendar = Calendar.getInstance().apply {
                set(1999, Calendar.JANUARY, 1, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val fixedTime = calendar.timeInMillis
            val time = if (isExit) System.currentTimeMillis() else fixedTime
            repository.updateTime(getUserUid()!!, time)
        }
    }


}