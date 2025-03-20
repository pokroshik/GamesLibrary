package com.example.gameslibrary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.data.models.ReviewModel
import com.example.gameslibrary.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    private val _games = MutableStateFlow<List<GameModel>>(emptyList())
    val games: StateFlow<List<GameModel>> = _games.asStateFlow()

    private val _reviews = MutableStateFlow<List<ReviewModel>>(emptyList())
    val reviews: StateFlow<List<ReviewModel>> = _reviews.asStateFlow()

    private var _game = MutableStateFlow<GameModel?>(null)
    var game: StateFlow<GameModel?> = _game.asStateFlow()

    private var lastLoadedGame: GameModel? = null
    var isLoading = false

    private val _selectedGenres = MutableStateFlow<List<String>>(emptyList())
    val selectedGenres: StateFlow<List<String>> = _selectedGenres

    private val _controllerSupport = MutableStateFlow(true)
    val controllerSupport: StateFlow<Boolean> = _controllerSupport

    private val _sortType = MutableStateFlow(false)
    val sortType: StateFlow<Boolean> = _sortType

    private val _gameCount = MutableStateFlow(49L)
    val gameCount: StateFlow<Long> = _gameCount.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    loadGames(query, reset = true)
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        val processedQuery = query.trim().split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }

        _searchQuery.value = processedQuery
    }

    private suspend fun loadGames(searchQuery: String, reset: Boolean = false) {
        if (reset) {
            lastLoadedGame = null
            _games.value = emptyList()
        }

        if (isLoading) return
        isLoading = true

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            repository.loadGamesPaged(
                limit = 10,
                lastGame = lastLoadedGame,
                genres = selectedGenres.value,
                controller = controllerSupport.value,
                sorting = sortType.value,
                searchQuery = searchQuery
            ).collect { newGames ->
                if (reset) {
                    _games.value = newGames
                } else {
                    _games.value += newGames
                }

                if (newGames.isNotEmpty()) {
                    lastLoadedGame = newGames.last()
                }

                isLoading = false
            }
        }
    }

    fun loadNextPage() {
        if (isLoading) return
        viewModelScope.launch {
            loadGames(_searchQuery.value, reset = false)
        }
    }

/*    fun loadNextPage(limit: Long = 10) {
        if (isLoading) return

        isLoading = true
        viewModelScope.launch {
            repository.loadGamesPaged(limit, lastLoadedGame, selectedGenres.value, controllerSupport.value, sortType.value).collect { newGames ->
                Log.d("ViewModel", "Получено игр: ${newGames.size}")
                if (newGames.isNotEmpty()) {
                    lastLoadedGame = newGames.last()
                    _games.value += newGames
                }
                isLoading = false
            }
        }
    }*/

    fun getGameByTitle(title: String, userId: String) {
        viewModelScope.launch {
            val game = repository.loadGameByTitle(title, userId)
            if (game != null) {
                _game.value = game
                loadReview(game.documentId)
            } else {
                _game.value = null
            }
        }
    }

    fun getGameCount(genres: List<String> = emptyList(), isController: Boolean) {
        viewModelScope.launch {
            val count = repository.loadGamesCount(genres, isController)
            _gameCount.value = count
        }
    }

    fun changeGameWishlist(gameId: String, userId: String) {
        viewModelScope.launch {
            val currentWish = _game.value?.isWish?: false
            repository.changeWishlist(gameId, userId, currentWish)
            val currentGame = _game.value
            if (currentGame != null) {
                _game.value = currentGame.copy(isWish = !currentWish)
            }
        }
    }

    fun changeReview(gameId: String, userId: String, userName: String,text: String, score: Int) {
        viewModelScope.launch {
            val review = ReviewModel(
                rating = score,
                comment = text,
                timestamp = System.currentTimeMillis(),
                user = userName,
                uid = userId
            )
            val currentWish = _game.value?.isWish?: false
            if (currentWish) {
                repository.changeWishlist(gameId, userId, currentWish)
            }
            val currentPlayed = _game.value?.isPlayed?: false
            repository.changeReviewToGame(gameId, userId, review)
            val currentGame = _game.value
            if (currentGame != null && !currentPlayed) {
                _game.value = currentGame.copy(isPlayed = true, isWish = false)
            }
        }
    }

    fun loadReview(id:String) {
        viewModelScope.launch {
            val reviews = repository.loadReviews(id)
            _reviews.value = reviews
        }
    }

    fun confirmFilter(genres: List<String>, support: Boolean, sort: Int) {
        _games.value = emptyList()
        lastLoadedGame = null

        _selectedGenres.value = genres
        _controllerSupport.value = support
        _sortType.value = sort == 0

        viewModelScope.launch {
            loadGames(_searchQuery.value, reset = false)
        }
    }
}