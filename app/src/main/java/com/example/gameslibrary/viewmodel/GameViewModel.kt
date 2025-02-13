package com.example.gameslibrary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    private val _games = MutableStateFlow<List<GameModel>>(emptyList())
    val games: StateFlow<List<GameModel>> = _games.asStateFlow()

    private val _game = MutableStateFlow<GameModel?>(null)
    val game: StateFlow<GameModel?> = _game.asStateFlow()

    private var lastLoadedGame: GameModel? = null
    private var isLoading = false

    private val _gameCount = MutableStateFlow(52L)
    val gameCount: StateFlow<Long> = _gameCount.asStateFlow()

    fun loadNextPage(limit: Long = 10) {
        if (isLoading) return

        isLoading = true
        viewModelScope.launch {
            repository.loadGamesPaged(limit, lastLoadedGame).collect { newGames ->
                Log.d("ViewModel", "Получено игр: ${newGames.size}")
                if (newGames.isNotEmpty()) {
                    lastLoadedGame = newGames.last()
                    _games.value += newGames
                }
                isLoading = false
            }
        }
    }

    fun getGameByTitle(title: String) {
        viewModelScope.launch {
            val game = repository.loadGameByTitle(title)
            _game.value = game
        }
    }

    fun getGameCount(genres: List<String> = emptyList()) {
        viewModelScope.launch {
            val count = repository.loadGamesCount(genres)
            _gameCount.value = count // Обновляем значение
        }
    }
}